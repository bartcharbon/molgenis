package org.molgenis.security.audit;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.molgenis.data.*;
import org.molgenis.data.meta.AttributeType;
import org.molgenis.data.meta.MetaDataService;
import org.molgenis.data.meta.model.Attribute;
import org.molgenis.data.meta.model.AttributeFactory;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.data.support.QueryImpl;
import org.molgenis.security.core.utils.SecurityUtils;
import org.molgenis.security.user.UserService;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

//TODO 1: figure out a way to select deleted records if requested
//TODO 2: add change info to the entity
//TODO 3: what to do if attributes are removed...

public class AuditedEntityRepositoryDecorator extends AbstractRepositoryDecorator<Entity>
{
	public static final String DELETED = "Deleted";
	public static final String OLDROWID = "oldRowId";
	public static final String CHANGEDATE = "changeDate";
	public static final String CHANGETYPE = "changeType";
	public static final String CHANGEINFO = "changeInfo";
	public static final String USER = "User";
	public static final String ID = "ID";

	private final Repository<Entity> decoratedRepo;
	private final UserService userService;
	private final AttributeFactory attributeFactory;
	private final ArrayList<String> auditedAttrs;
	MetaDataService metaDataService;

	public AuditedEntityRepositoryDecorator(Repository<Entity> decoratedRepo, UserService userService,
			AttributeFactory attributeFactory, MetaDataService metaDataService)
	{
		this.decoratedRepo = requireNonNull(decoratedRepo);
		this.userService = userService;
		this.attributeFactory = attributeFactory;
		this.metaDataService = metaDataService;
		this.auditedAttrs = new ArrayList<String>();
		auditedAttrs.addAll(Arrays
				.asList(DELETED, OLDROWID, CHANGEDATE, CHANGEINFO, CHANGETYPE, USER, ID, "HistorySelfReference"));
	}

	@Override
	protected Repository<Entity> delegate()
	{
		return decoratedRepo;
	}

	@Override
	public Query<Entity> query()
	{
		return new QueryImpl<>(this);
	}

	@Override
	public Iterator<Entity> iterator()
	{
		return StreamSupport.stream(decoratedRepo.spliterator(), false)
				.filter(entity -> entity.getBoolean(DELETED) == false).iterator();
	}

	@Override
	public void forEachBatched(Fetch fetch, Consumer<List<Entity>> consumer, int batchSize)
	{
		decoratedRepo.forEachBatched(fetch, entities ->
		{
			entities = entities.stream().filter(entity -> entity.getBoolean(DELETED).booleanValue() == false)
					.collect(Collectors.toList());
			consumer.accept(entities);

		}, batchSize);
	}

	@Override
	public long count()
	{
		return count(new QueryImpl<>().eq(DELETED, false));
	}

	@Override
	public long count(Query<Entity> q)
	{
		if (!q.getRules().stream().map(rule -> rule.getField()).collect(Collectors.toList())
				.contains(getEntityType().getIdAttribute().getName()))
		{
			if (q.getRules().size() > 0) q = q.and();
			q = q.eq(DELETED, false);
		}
		return decoratedRepo.count(q);
	}

	@Override
	public Stream<Entity> findAll(Query<Entity> q)
	{
		if (!q.getRules().stream().map(rule -> rule.getField()).collect(Collectors.toList())
				.contains(getEntityType().getIdAttribute().getName()))
		{
			if (q.getRules().size() > 0) q = q.and();
			q = q.eq(DELETED, false);
		}
		return decoratedRepo.findAll(q);
	}

	@Override
	public Entity findOne(Query<Entity> q)
	{
		if (!q.getRules().stream().map(rule -> rule.getField()).collect(Collectors.toList())
				.contains(getEntityType().getIdAttribute().getName()))
		{
			if (q.getRules().size() > 0) q = q.and();
			q = q.eq(DELETED, false);
		}
		return decoratedRepo.findOne(q);
	}

	@Override
	public void delete(Entity entity)
	{
		entity.set(DELETED, true);
		persistPreviousVersion(entity, "delete");
		decoratedRepo.update(entity);
	}

	@Override
	public void delete(Stream<Entity> entities)
	{
		entities.forEach(entity -> persistPreviousVersion(entity, "delete"));
		decoratedRepo.delete(entities);
	}

	@Override
	public void deleteById(Object id)
	{
		Entity entity = findOneById(id);
		persistPreviousVersion(entity, "delete");
		decoratedRepo.delete(entity);
	}

	@Override
	public void deleteAll(Stream<Object> ids)
	{
		Stream<Entity> entities = findAll(ids);
		entities.forEach(entity -> persistPreviousVersion(entity, "delete"));
		decoratedRepo.deleteAll(ids);
	}

	@Override
	public void deleteAll()
	{
		Stream<Entity> entities = findAll(new QueryImpl<>().eq(DELETED, false));
		entities.forEach(entity -> persistPreviousVersion(entity, "delete"));
		decoratedRepo.deleteAll();
	}

	@Override
	public void add(Entity entity)
	{
		persistPreviousVersion(entity, "add");
	}

	@Override
	public Integer add(Stream<Entity> entities)
	{
		System.out.println("Integer add(Stream<Entity> entities)");
		entities.forEach(entity -> persistPreviousVersion(entity, "add"));
		return -1;
	}

	@Override
	public void update(Entity entity)
	{
		System.out.println("update(Entity entity)");
		String id = persistPreviousVersion(entity, "update");
		if (id != null)
		{
			Entity entity1 = addHistoryReference(entity, id);
			decoratedRepo.update(entity1);
		}
	}

	@Override
	public void update(Stream<Entity> entities)
	{
		System.out.println("update(Stream<Entity> entities)");
		Stream<Entity> mappedEntities = entities.map(entity ->
		{
			String archivedEntityId = persistPreviousVersion(entity, "update");
			if (archivedEntityId != null)
			{
				return addHistoryReference(entity, archivedEntityId);
			}
			else return null;
		});
		decoratedRepo.update(mappedEntities.filter(entity -> entity != null));
	}

	private Entity addHistoryReference(Entity entity, String archivedEntityId)
	{
		List<Entity> references = Lists.newArrayList(entity.getEntities("HistorySelfReference"));
		Entity oldEntity = findOneById(archivedEntityId);
		if (oldEntity != null) references.add(oldEntity);
		entity.set("HistorySelfReference", references);
		return entity;
	}

	private String persistPreviousVersion(Entity entity, String changeType)
	{
		init();
		Entity currentEntity = entity;
		if (!changeType.equals("add"))
		{
			currentEntity = findOneById(entity.getIdValue());
			currentEntity.set(OLDROWID, currentEntity.getIdValue());
			currentEntity.setIdValue(entity.getIdValue().toString() + new Date());
			currentEntity.set(DELETED, true);
		}
		entity.set(CHANGEDATE, new Date());
		entity.set(CHANGETYPE, changeType);
		entity.set(USER, userService.getUser(SecurityUtils.getCurrentUsername()));
		currentEntity.setIdValue(entity.getIdValue().toString() + new Date().getTime());
		if (changeType.equals("update"))
		{
			List<String> changedAttributes = new ArrayList();
			Iterator<Attribute> it = getEntityType().getAllAttributes().iterator();
			while (it.hasNext())
			{
				Attribute attr = it.next();
				if (!auditedAttrs.contains(attr.getName()) && !attr.getName()
						.equals(getEntityType().getIdAttribute().getName()))
					attributeChanged(changedAttributes, attr.getName(), currentEntity, entity);
			}
			entity.set(CHANGEINFO, StringUtils.join(changedAttributes, ','));
			if (changedAttributes.size() == 0)
			{
				return null;
			}
		}
		decoratedRepo.add(currentEntity);
		return currentEntity.getIdValue().toString();
	}

	private void attributeChanged(List<String> changedAttributes, String attr, Entity oldEntity, Entity newEntity)
	{
		Object oldValue = oldEntity.get(attr);
		Object newValue = newEntity.get(attr);
		if (!Objects.equals(oldValue, newValue))
		{
			changedAttributes.add(attr);
		}
	}

	private void init()
	{
		if (!hasHistoryReference()) addHistoryReferenceAttribute();
	}

	public boolean hasHistoryReference()
	{
		return getEntityType().getAttribute("HistorySelfReference") != null;
	}

	//TODO: set visible to false once we have a suitable entity report
	public void addHistoryReferenceAttribute()
	{
		Attribute attr = attributeFactory.create();
		attr.setDataType(AttributeType.MREF);
		attr.setRefEntity(getEntityType());
		attr.setName("HistorySelfReference");
		attr.setLabel("History");
		attr.setDescription("This attribute references previous versions of this entity");
		EntityType entityType = getEntityType().addAttribute(attr);
		metaDataService.updateEntityType(entityType);
	}

}

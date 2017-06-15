package org.molgenis.app.decorator;

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

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.molgenis.app.decorator.VersionedEntityType.*;

public class VersionedEntityRepositoryDecorator extends AbstractRepositoryDecorator<Entity>
{
	private Repository<Entity> decoratedRepo;
	private final UserService userService;
	private final ArrayList<String> auditedAttrs;
	private final AttributeFactory attributeFactory;
	private final MetaDataService metaDataService;

	public VersionedEntityRepositoryDecorator(UserService userService, Repository repo,
			AttributeFactory attributeFactory, MetaDataService metaDataService)
	{
		this.userService = userService;
		this.decoratedRepo = repo;
		this.attributeFactory = attributeFactory;
		this.metaDataService = metaDataService;

		this.auditedAttrs = new ArrayList<>();
		auditedAttrs.addAll(Arrays
				.asList(DELETED, OLD_ROW_ID, CHANGE_DATE, CHANGE_INFO, CHANGE_TYPE, USER, ID, HISTORY_SELF_REFERENCE));
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
				.filter(entity -> entity.getBoolean(DELETED) != true).iterator();
	}

	@Override
	public void forEachBatched(Fetch fetch, Consumer<List<Entity>> consumer, int batchSize)
	{
		decoratedRepo.forEachBatched(fetch, entities ->
		{
			entities = entities.stream().filter(entity -> entity.getBoolean(DELETED).booleanValue() != true)
					.collect(Collectors.toList());
			consumer.accept(entities);

		}, batchSize);
	}

	@Override
	public long count()
	{
		return count(new QueryImpl<>().not().eq(DELETED, true));
	}

	@Override
	public long count(Query<Entity> q)
	{
		if (!q.getRules().stream().map(rule -> rule.getField()).collect(Collectors.toList())
				.contains(getEntityType().getIdAttribute().getName()))
		{
			if (q.getRules().size() > 0) q = q.and();
			q = q.not().eq(DELETED, true);
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
			q = q.not().eq(DELETED, true);
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
			q = q.not().eq(DELETED, true);
		}
		return decoratedRepo.findOne(q);
	}

	@Override
	public void delete(Entity entity)
	{
		entity.set(DELETED, true);
		persistPreviousVersion(entity, DELETE);
		decoratedRepo.update(entity);
	}

	@Override
	public void delete(Stream<Entity> entities)
	{
		entities.forEach(entity -> persistPreviousVersion(entity, DELETE));
		decoratedRepo.delete(entities);
	}

	@Override
	public void deleteById(Object id)
	{
		Entity entity = findOneById(id);
		persistPreviousVersion(entity, DELETE);
		decoratedRepo.delete(entity);
	}

	@Override
	public void deleteAll(Stream<Object> ids)
	{
		Stream<Entity> entities = findAll(ids);
		entities.forEach(entity -> persistPreviousVersion(entity, DELETE));
		decoratedRepo.deleteAll(ids);
	}

	@Override
	public void deleteAll()
	{
		Stream<Entity> entities = findAll(new QueryImpl<>().not().eq(DELETED, true));
		entities.forEach(entity -> persistPreviousVersion(entity, DELETE));
		decoratedRepo.deleteAll();
	}

	@Override
	public void add(Entity entity)
	{
		persistPreviousVersion(entity, ADD);
	}

	@Override
	public Integer add(Stream<Entity> entities)
	{
		final AtomicInteger count = new AtomicInteger();
		entities.forEach(entity ->
		{
			persistPreviousVersion(entity, ADD);
			count.addAndGet(1);
		});
		return count.get();
	}

	@Override
	public void update(Entity entity)
	{
		String id = persistPreviousVersion(entity, UPDATE);
		if (id != null)
		{
			Entity entity1 = addHistoryReference(entity, id);
			decoratedRepo.update(entity1);
		}
	}

	@Override
	public void update(Stream<Entity> entities)
	{
		Stream<Entity> mappedEntities = entities.map(entity ->
		{
			String archivedEntityId = persistPreviousVersion(entity, UPDATE);
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
		List<Entity> references = Lists.newArrayList(entity.getEntities(HISTORY_SELF_REFERENCE));
		Entity oldEntity = findOneById(archivedEntityId);
		if (oldEntity != null) references.add(oldEntity);
		entity.set(HISTORY_SELF_REFERENCE, references);
		return entity;
	}

	private String persistPreviousVersion(Entity entity, String changeType)
	{
		//FIXME: how to do this cleaner
		if (!hasSelfReferenceAttr(delegate().getEntityType()))
		{
			addSelfReferenceAttr(delegate().getEntityType());
		}

		Entity currentEntity = entity;
		if (!changeType.equals(ADD))
		{
			currentEntity = findOneById(entity.getIdValue());
			currentEntity.set(OLD_ROW_ID, currentEntity.getIdValue());
			currentEntity.setIdValue(entity.getIdValue().toString() + Instant.now());
			currentEntity.set(DELETED, true);
		}
		entity.set(CHANGE_DATE, Instant.now());
		entity.set(CHANGE_TYPE, changeType);
		entity.set(USER, userService.getUser(SecurityUtils.getCurrentUsername()));
		currentEntity.setIdValue(entity.getIdValue().toString() + Instant.now());
		if (changeType.equals(UPDATE))
		{
			String changedAttributes = determineChangedAttributes(currentEntity, entity);
			entity.set(CHANGE_INFO, changedAttributes);
			if (!StringUtils.isNotEmpty(changedAttributes))
			{
				return null;
			}
		}
		decoratedRepo.add(currentEntity);
		return currentEntity.getIdValue().toString();
	}

	private String determineChangedAttributes(Entity currentEntity, Entity entity)
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
		return StringUtils.join(changedAttributes, ',');

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

	public void addSelfReferenceAttr(EntityType entityType)
	{
		if (!hasSelfReferenceAttr(entityType))
		{
			Attribute attr = attributeFactory.create();
			attr.setDataType(AttributeType.MREF);
			attr.setRefEntity(entityType);
			attr.setName(HISTORY_SELF_REFERENCE);
			attr.setLabel("History");
			attr.setVisible(false);
			attr.setDescription("This attribute references previous versions of this entity");
			entityType.addAttribute(attr);
			metaDataService.updateEntityType(entityType);
		}
	}

	public boolean hasSelfReferenceAttr(EntityType entityType)
	{
		return entityType.getAttribute("HistorySelfReference") != null;
	}
}

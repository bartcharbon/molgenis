package org.molgenis.security.audit;

import org.molgenis.data.*;
import org.molgenis.data.support.QueryImpl;
import org.molgenis.security.core.utils.SecurityUtils;
import org.molgenis.security.user.UserService;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

//TODO 1: figure out a way to select deleted records if requested
//TODO 2: add change info to the entity
//TODO 3: plugin to browse the history (needs TODO 1)
//TODO 4: restore functionality (needs TODO 1 and 3)
//TODO 5: implement ID based operations

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

	public AuditedEntityRepositoryDecorator(Repository<Entity> decoratedRepo, UserService userService)
	{
		this.decoratedRepo = requireNonNull(decoratedRepo);
		this.userService = userService;
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
		if (q.getRules().size() > 0) q = q.and();
		q = q.eq(DELETED, false);
		return decoratedRepo.count(q);
	}

	@Override
	public Stream<Entity> findAll(Query<Entity> q)
	{
		if (q.getRules().size() > 0) q = q.and();
		q = q.eq(DELETED, false);
		return decoratedRepo.findAll(q);
	}

	@Override
	public Entity findOne(Query<Entity> q)
	{
		if (q.getRules().size() > 0) q = q.and();
		q = q.eq(DELETED, false);
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
		entities.forEach(entity -> persistPreviousVersion(entity, "add"));
		return -1;
	}

	@Override
	public void update(Entity entity)
	{
		persistPreviousVersion(entity, "update");
		decoratedRepo.update(entity);
	}

	@Override
	public void update(Stream<Entity> entities)
	{
		entities.forEach(entity -> persistPreviousVersion(entity, "update"));
		decoratedRepo.update(entities);
	}

	private void persistPreviousVersion(Entity entity, String changeType)
	{
		Entity currentEntity = entity;
		if(!changeType.equals("add"))
		{
			currentEntity = findOneById(entity.getIdValue());
			currentEntity.set(OLDROWID, currentEntity.getIdValue());
			currentEntity.setIdValue(entity.getIdValue().toString() + new Date());
			currentEntity.set(DELETED, true);
		}
		currentEntity.set(CHANGEDATE, new Date());
		currentEntity.set(CHANGETYPE, changeType);
		currentEntity.set(CHANGEINFO, "TODO");
		currentEntity.set(USER, userService.getUser(SecurityUtils.getCurrentUsername()));
		decoratedRepo.add(currentEntity);
	}
}

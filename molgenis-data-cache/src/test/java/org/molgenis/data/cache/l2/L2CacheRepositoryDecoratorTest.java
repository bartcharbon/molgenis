package org.molgenis.data.cache.l2;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.molgenis.data.Entity;
import org.molgenis.data.EntityKey;
import org.molgenis.data.Repository;
import org.molgenis.data.meta.model.EntityMetaData;
import org.molgenis.data.transaction.TransactionInformation;
import org.molgenis.test.data.AbstractMolgenisSpringTest;
import org.molgenis.test.data.EntityTestHarness;
import org.molgenis.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.molgenis.data.RepositoryCapability.CACHEABLE;
import static org.molgenis.data.RepositoryCapability.WRITABLE;
import static org.testng.Assert.*;

public class L2CacheRepositoryDecoratorTest extends AbstractMolgenisSpringTest
{
	private L2CacheRepositoryDecorator l2CacheRepositoryDecorator;
	@Mock
	private L2Cache l2Cache;
	@Mock
	private Repository<Entity> decoratedRepository;
	@Mock
	private TransactionInformation transactionInformation;
	@Autowired
	private EntityTestHarness entityTestHarness;
	private List<Entity> entities;
	@Captor
	private ArgumentCaptor<Iterable<Object>> cacheIdCaptor;
	@Captor
	private ArgumentCaptor<Stream<Object>> repoIdCaptor;
	private EntityMetaData emd;

	@BeforeClass
	public void beforeClass()
	{
		initMocks(this);

		emd = entityTestHarness.createDynamicRefEntityMetaData();
		when(decoratedRepository.getEntityMetaData()).thenReturn(emd);
		entities = entityTestHarness.createTestRefEntities(emd, 4);

		when(decoratedRepository.getCapabilities()).thenReturn(Sets.newHashSet(CACHEABLE, WRITABLE));
		l2CacheRepositoryDecorator = new L2CacheRepositoryDecorator(decoratedRepository, l2Cache,
				transactionInformation);
	}

	@BeforeMethod
	public void beforeMethod()
	{
		reset(l2Cache);
	}

	@Test
	public void testFindOneByIdNotDirtyCacheableAndPresent()
	{
		when(transactionInformation.isEntityDirty(EntityKey.create(emd, "0"))).thenReturn(false);
		when(l2Cache.get(decoratedRepository, "0")).thenReturn(entities.get(0));
		assertEquals(l2CacheRepositoryDecorator.findOneById("0"), entities.get(0));
	}

	@Test
	public void testFindOneByIdNotDirtyCacheableNotPresent()
	{
		when(transactionInformation.isEntityDirty(EntityKey.create(emd, "0"))).thenReturn(false);
		when(l2Cache.get(decoratedRepository, "abcde")).thenReturn(null);
		assertNull(l2CacheRepositoryDecorator.findOneById("abcde"));
	}

	@Test
	public void testFindOneByIdDirty()
	{
		when(transactionInformation.isEntityDirty(EntityKey.create(emd, "0"))).thenReturn(true);
		when(decoratedRepository.findOneById("0")).thenReturn(entities.get(0));
		assertEquals(l2CacheRepositoryDecorator.findOneById("0"), entities.get(0));
	}

	@Test
	public void testFindOneByIdEntireRepositoryDirty()
	{
		when(transactionInformation.isRepositoryDirty(emd.getName())).thenReturn(true);
		when(decoratedRepository.findOneById("0")).thenReturn(entities.get(0));
		assertEquals(l2CacheRepositoryDecorator.findOneById("0"), entities.get(0));
	}

	@Test
	public void testFindAllSplitsIdsOnTransactionInformation()
	{
		// 0: Dirty, not present in repo
		// 1: Dirty, present in decorated repo
		// 2: Not dirty, present in cache
		// 3: Not dirty, absence stored in cache

		when(transactionInformation.isEntityDirty(EntityKey.create(emd, "0"))).thenReturn(true);
		when(transactionInformation.isEntityDirty(EntityKey.create(emd, "1"))).thenReturn(true);
		when(transactionInformation.isEntityDirty(EntityKey.create(emd, "2"))).thenReturn(false);
		when(transactionInformation.isEntityDirty(EntityKey.create(emd, "3"))).thenReturn(false);

		Stream<Object> ids = Lists.<Object>newArrayList("0", "1", "2", "3").stream();
		when(l2Cache.getBatch(eq(decoratedRepository), cacheIdCaptor.capture()))
				.thenReturn(newArrayList(entities.get(3)));
		when(decoratedRepository.findAll(repoIdCaptor.capture())).thenReturn(of(entities.get(1)));

		List<Entity> retrievedEntities = l2CacheRepositoryDecorator.findAll(ids).collect(toList());

		List<Object> decoratedRepoIds = repoIdCaptor.getValue().collect(toList());
		assertEquals(decoratedRepoIds, Lists.newArrayList("0", "1"));
		List<Object> cacheIds = Lists.newArrayList(cacheIdCaptor.getValue());
		assertEquals(cacheIds, Lists.newArrayList("2", "3"));

		assertEquals(retrievedEntities.size(), 2);
		assertTrue(EntityUtils.equals(retrievedEntities.get(0), entities.get(1)));
		assertTrue(EntityUtils.equals(retrievedEntities.get(1), entities.get(3)));
	}
}

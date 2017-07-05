package org.molgenis.dataexplorer.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.molgenis.data.DataService;
import org.molgenis.data.meta.MetaDataService;
import org.molgenis.data.meta.model.Attribute;
import org.molgenis.data.meta.model.EntityType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class GenomeBrowserServiceTest
{
	@Mock
	DataService dataService;

	@InjectMocks
	GenomeBrowserService genomeBrowserService;

	@BeforeMethod
	public void beforeMethode()
	{
		dataService = mock(DataService.class);
		initMocks(this);
	}

	@Test
	public void testGetGenomeBrowserEntities() throws Exception
	{
		MetaDataService metaDataService = mock(MetaDataService.class);
		when(dataService.getMeta()).thenReturn(metaDataService);

		EntityType genomeEntityType = mock(EntityType.class);
		when(genomeEntityType.isAbstract()).thenReturn(false);
		when(genomeEntityType.getId()).thenReturn("FullyQualifiedName");
		when(genomeEntityType.getLabel()).thenReturn("Label");
		EntityType abtractGenomeEntityType = mock(EntityType.class);
		when(abtractGenomeEntityType.isAbstract()).thenReturn(true);
		EntityType nonGenomeEntityType = mock(EntityType.class);
		when(nonGenomeEntityType.isAbstract()).thenReturn(false);

		Attribute attribute = mock(Attribute.class);

		Stream<EntityType> entityTypes = Stream.of(genomeEntityType, abtractGenomeEntityType, nonGenomeEntityType);
		when(metaDataService.getEntityTypes()).thenReturn(entityTypes);

		List<EntityType> resultList = genomeBrowserService.getGenomeBrowserEntities().collect(Collectors.toList());
		assertEquals(resultList.size(), 1);
		assertEquals(resultList.get(0), genomeEntityType);

	}

}
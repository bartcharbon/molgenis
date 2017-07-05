package org.molgenis.dataexplorer.service;

import org.molgenis.data.DataService;
import org.molgenis.data.meta.model.Attribute;
import org.molgenis.data.meta.model.AttributeMetadata;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.data.support.QueryImpl;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Service implements genomeBrowser specific business logic.
 */
@Component
public class GenomeBrowserService
{

	private final DataService dataService;
	private final AttributeMetadata attributeMetadata;

	public GenomeBrowserService(DataService dataService, AttributeMetadata attributeMetadata)
	{
		this.dataService = requireNonNull(dataService);
		this.attributeMetadata = requireNonNull(attributeMetadata);
	}

	/**
	 * Fetch all non abstract genomeBrowser entities
	 * these are defined as being non abstract and having a ATTRS_POS and ATTRS_CHROM attribute.
	 *
	 * @return from entity name to entityLabel
	 */
	public Stream<EntityType> getGenomeBrowserEntities()
	{
		//get default attrs

		Stream<Attribute> attrs = dataService
				.findAll(attributeMetadata.getId(), new QueryImpl<Attribute>().eq(attributeMetadata.NAME, "#CHROM"),
						Attribute.class);
		//get attrs where name is CHROM
		//get entities
		//check if POS is also present

		//add configured tracks to set
		return dataService.getMeta().getEntityTypes().filter(entityType -> !entityType.isAbstract())
				.filter(this::isGenomeBrowserEntity);
	}

	private boolean isGenomeBrowserEntity(EntityType entityType)
	{
		return true;
	}
}

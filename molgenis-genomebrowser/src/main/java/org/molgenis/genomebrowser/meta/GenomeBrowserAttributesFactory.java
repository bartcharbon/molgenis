package org.molgenis.genomebrowser.meta;

import org.molgenis.data.AbstractSystemEntityFactory;
import org.molgenis.data.populate.EntityPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenomeBrowserAttributesFactory
		extends AbstractSystemEntityFactory<GenomeBrowserAttributes, GenomeBrowserAttributesMetadata, String>
{
	@Autowired
	GenomeBrowserAttributesFactory(GenomeBrowserAttributesMetadata myEntityMeta, EntityPopulator entityPopulator)
	{
		super(GenomeBrowserAttributes.class, myEntityMeta, entityPopulator);
	}
}
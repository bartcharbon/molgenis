package org.molgenis.dataexplorer.controller.bioschema.model.meta.factory;

import org.molgenis.data.AbstractSystemEntityFactory;
import org.molgenis.data.populate.EntityPopulator;
import org.molgenis.dataexplorer.controller.bioschema.model.Provider;
import org.molgenis.dataexplorer.controller.bioschema.model.meta.ProviderMetadata;
import org.springframework.stereotype.Component;

@Component
public class ProviderFactory
    extends AbstractSystemEntityFactory<Provider, ProviderMetadata, String> {
  ProviderFactory(ProviderMetadata schemaMetadata, EntityPopulator entityPopulator) {
    super(Provider.class, schemaMetadata, entityPopulator);
  }
}

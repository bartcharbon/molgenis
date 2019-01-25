package org.molgenis.dataexplorer.controller.bioschema.model.meta.factory;

import org.molgenis.data.AbstractSystemEntityFactory;
import org.molgenis.data.populate.EntityPopulator;
import org.molgenis.dataexplorer.controller.bioschema.model.Dataset;
import org.molgenis.dataexplorer.controller.bioschema.model.meta.DatasetMetadata;
import org.springframework.stereotype.Component;

@Component
public class DatasetFactory extends AbstractSystemEntityFactory<Dataset, DatasetMetadata, String> {
  DatasetFactory(DatasetMetadata schemaMetadata, EntityPopulator entityPopulator) {
    super(Dataset.class, schemaMetadata, entityPopulator);
  }
}

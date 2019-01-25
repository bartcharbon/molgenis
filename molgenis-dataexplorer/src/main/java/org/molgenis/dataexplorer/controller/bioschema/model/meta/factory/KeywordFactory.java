package org.molgenis.dataexplorer.controller.bioschema.model.meta.factory;

import org.molgenis.data.AbstractSystemEntityFactory;
import org.molgenis.data.populate.EntityPopulator;
import org.molgenis.dataexplorer.controller.bioschema.model.Schema;
import org.molgenis.dataexplorer.controller.bioschema.model.meta.SchemaMetadata;
import org.springframework.stereotype.Component;

@Component
public class KeywordFactory extends AbstractSystemEntityFactory<Schema, SchemaMetadata, String> {
  KeywordFactory(SchemaMetadata schemaMetadata, EntityPopulator entityPopulator) {
    super(Schema.class, schemaMetadata, entityPopulator);
  }
}

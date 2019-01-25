package org.molgenis.dataexplorer.controller.bioschema.model.meta.factory;

import org.molgenis.data.AbstractSystemEntityFactory;
import org.molgenis.data.populate.EntityPopulator;
import org.molgenis.dataexplorer.controller.bioschema.model.Schema;
import org.molgenis.dataexplorer.controller.bioschema.model.meta.SchemaMetadata;
import org.springframework.stereotype.Component;

@Component
public class SchemaFactory extends AbstractSystemEntityFactory<Schema, SchemaMetadata, String> {
  SchemaFactory(SchemaMetadata schemaMetadata, EntityPopulator entityPopulator) {
    super(Schema.class, schemaMetadata, entityPopulator);
  }
}

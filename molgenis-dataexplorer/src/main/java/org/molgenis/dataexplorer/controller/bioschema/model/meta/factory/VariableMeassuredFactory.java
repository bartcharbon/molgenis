package org.molgenis.dataexplorer.controller.bioschema.model.meta.factory;

import org.molgenis.data.AbstractSystemEntityFactory;
import org.molgenis.data.populate.EntityPopulator;
import org.molgenis.dataexplorer.controller.bioschema.model.VariableMeassured;
import org.molgenis.dataexplorer.controller.bioschema.model.meta.VariableMeassuredMetadata;
import org.springframework.stereotype.Component;

@Component
public class VariableMeassuredFactory
    extends AbstractSystemEntityFactory<VariableMeassured, VariableMeassuredMetadata, String> {
  VariableMeassuredFactory(
      VariableMeassuredMetadata schemaMetadata, EntityPopulator entityPopulator) {
    super(VariableMeassured.class, schemaMetadata, entityPopulator);
  }
}

package org.molgenis.dataexplorer.controller.bioschema.model.meta.factory;

import org.molgenis.data.AbstractSystemEntityFactory;
import org.molgenis.data.populate.EntityPopulator;
import org.molgenis.dataexplorer.controller.bioschema.model.PublicationEvent;
import org.molgenis.dataexplorer.controller.bioschema.model.meta.PublicationEventMetadata;
import org.springframework.stereotype.Component;

@Component
public class PublicationEventFactory
    extends AbstractSystemEntityFactory<PublicationEvent, PublicationEventMetadata, String> {
  PublicationEventFactory(
      PublicationEventMetadata schemaMetadata, EntityPopulator entityPopulator) {
    super(PublicationEvent.class, schemaMetadata, entityPopulator);
  }
}

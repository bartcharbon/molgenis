package org.molgenis.dataexplorer.controller.bioschema.model;

import static org.molgenis.dataexplorer.controller.bioschema.model.meta.PublicationEventMetadata.*;

import org.molgenis.data.Entity;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.data.support.StaticEntity;

public class PublicationEvent extends StaticEntity {
  public PublicationEvent(Entity entity) {
    super(entity);
  }

  public PublicationEvent(EntityType entityType) {
    super(entityType);
  }

  public PublicationEvent(String id, EntityType entityType) {
    super(entityType);
    setName(id);
  }

  public String getName() {
    return getString(NAME);
  }

  public void setName(String name) {
    set(NAME, name);
  }

  public String getUrl() {
    return getString(URL);
  }

  public void setUrl(String url) {
    set(URL, url);
  }

  public String getBioschemaString() {
    return this.getClass().getSimpleName() + "_TODO_";
  }
}

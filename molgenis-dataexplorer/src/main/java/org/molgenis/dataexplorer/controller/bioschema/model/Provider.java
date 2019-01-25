package org.molgenis.dataexplorer.controller.bioschema.model;

import static org.molgenis.dataexplorer.controller.bioschema.model.meta.ProviderMetadata.*;

import org.molgenis.data.Entity;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.data.support.StaticEntity;
import org.molgenis.dataexplorer.controller.bioschema.model.enums.ProviderType;

public class Provider extends StaticEntity {
  public Provider(Entity entity) {
    super(entity);
  }

  public Provider(EntityType entityType) {
    super(entityType);
  }

  public Provider(String id, EntityType entityType) {
    super(entityType);
    setName(id);
  }

  public ProviderType getType() {
    return ProviderType.valueOf(getString(TYPE));
  }

  public void setType(ProviderType type) {
    set(TYPE, ProviderType.getValueString(type));
  }

  public String getName() {
    return getString(NAME);
  }

  public void setName(String name) {
    set(NAME, name);
  }

  public String getEmail() {
    return getString(EMAIL);
  }

  public void setEmail(String email) {
    set(EMAIL, email);
  }

  public String getBioschemaString() {
    return this.getClass().getSimpleName() + "_TODO_";
  }
}

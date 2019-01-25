package org.molgenis.dataexplorer.controller.bioschema.model;

import static org.molgenis.dataexplorer.controller.bioschema.model.meta.DatasetMetadata.*;

import org.molgenis.data.Entity;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.data.support.StaticEntity;
import org.molgenis.dataexplorer.controller.bioschema.model.enums.Type;

public class VariableMeassured extends StaticEntity {
  public VariableMeassured(Entity entity) {
    super(entity);
  }

  public VariableMeassured(EntityType entityType) {
    super(entityType);
  }

  public VariableMeassured(String id, EntityType entityType) {
    super(entityType);
    setName(id);
  }

  public Type getType() {
    return Type.valueOf(getString(TYPE));
  }

  public void setType(Type type) {
    set(TYPE, Type.getValueString(type));
  }

  public String getUrl() {
    return getString(URL);
  }

  public void setUrl(String url) {
    set(URL, url);
  }

  public String getName() {
    return getString(NAME);
  }

  public void setName(String name) {
    set(NAME, name);
  }

  public String getBioschemaString() {
    return this.getClass().getSimpleName() + "_TODO_";
  }
}

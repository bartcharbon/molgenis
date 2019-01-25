package org.molgenis.dataexplorer.controller.bioschema.model;

import static org.molgenis.dataexplorer.controller.bioschema.model.meta.KeywordMetadata.*;

import org.molgenis.data.Entity;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.data.support.StaticEntity;

public class Keyword extends StaticEntity {
  public Keyword(Entity entity) {
    super(entity);
  }

  public Keyword(EntityType entityType) {
    super(entityType);
  }

  public Keyword(String id, EntityType entityType) {
    super(entityType);
    setKeyword(id);
  }

  public String getKeyword() {
    return getString(KEYWORDD);
  }

  public void setKeyword(String keyword) {
    set(KEYWORDD, keyword);
  }

  public String getBioschemaString() {
    return this.getClass().getSimpleName() + "_TODO_";
  }
}

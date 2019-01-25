package org.molgenis.dataexplorer.controller.bioschema.model;

import static org.molgenis.dataexplorer.controller.bioschema.model.enums.Type.getValueString;
import static org.molgenis.dataexplorer.controller.bioschema.model.meta.DatasetMetadata.*;

import com.google.common.collect.Lists;
import java.util.List;
import org.molgenis.data.Entity;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.data.support.StaticEntity;
import org.molgenis.dataexplorer.controller.bioschema.model.enums.Type;

public class Dataset extends StaticEntity {
  public Dataset(Entity entity) {
    super(entity);
  }

  public Dataset(EntityType entityType) {
    super(entityType);
  }

  public Dataset(String id, EntityType entityType) {
    super(entityType);
    setIdentifier(id);
  }

  public Type getType() {
    return Type.valueOf(getString(TYPE));
  }

  public void setType(Type type) {
    set(TYPE, getValueString(type));
  }

  public String getName() {
    return getString(NAME);
  }

  public void setName(String name) {
    set(NAME, name);
  }

  public String getDescription() {
    return getString(DESCRIPTION);
  }

  public void setDescription(String description) {
    set(DESCRIPTION, description);
  }

  public String getIdentifier() {
    return getString(IDENTIFIER);
  }

  public void setIdentifier(String identifier) {
    set(IDENTIFIER, identifier);
  }

  public String getUrl() {
    return getString(URL);
  }

  public void setUrl(String url) {
    set(URL, url);
  }

  public List<Keyword> getKeywords() {
    return Lists.newArrayList(getEntities(KEYWORDS, Keyword.class));
  }

  public void setKeywords(List<Keyword> keywords) {
    set(KEYWORDS, keywords);
  }

  public List<VariableMeassured> getVariablesMeasured() {
    return Lists.newArrayList(getEntities(KEYWORDS, VariableMeassured.class));
  }

  public void setVariablesMeasured(List<VariableMeassured> variablesMeasured) {
    set(VARIABLESMEASSURED, variablesMeasured);
  }

  public String getBioschemaString() {
    return this.getClass().getSimpleName() + "_TODO_";
  }
}

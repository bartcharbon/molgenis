package org.molgenis.dataexplorer.controller.bioschema.model;

import static org.molgenis.dataexplorer.controller.bioschema.model.meta.SchemaMetadata.*;

import com.google.common.collect.Lists;
import java.util.List;
import org.molgenis.data.Entity;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.data.support.StaticEntity;
import org.molgenis.dataexplorer.controller.bioschema.model.enums.Type;

public class Schema extends StaticEntity {
  public Schema(Entity entity) {
    super(entity);
  }

  public Schema(EntityType entityType) {
    super(entityType);
  }

  public Schema(String id, EntityType entityType) {
    super(entityType);
    setIdentifier(id);
  }

  public String getContext() {
    return getString(CONTEXT);
  }

  public void setContext(String context) {
    set(CONTEXT, context);
  }

  public Type getType() {
    return Type.valueOf(getString(TYPE));
  }

  public void setType(Type type) {
    set(TYPE, Type.getValueString(type));
  }

  public String getIdentifier() {
    return getString(IDENTIFIER);
  }

  public void setIdentifier(String identifier) {
    set(IDENTIFIER, identifier);
  }

  public String getSourceOrganization() {
    return getString(SOURCEORGANISATION);
  }

  public void setSourceOrganization(String sourceOrganization) {
    set(SOURCEORGANISATION, sourceOrganization);
  }

  public PublicationEvent getPublication() {
    return getEntity(PUBLICATION, PublicationEvent.class);
  }

  public void setPublication(PublicationEvent publication) {
    set(PUBLICATION, publication);
  }

  public String getDescription() {
    return getString(DESCRIPTION);
  }

  public void setDescription(String description) {
    set(DESCRIPTION, description);
  }

  public String getUrl() {
    return getString(URL);
  }

  public void setUrl(String url) {
    set(URL, url);
  }

  public List<Dataset> getDataset() {
    return Lists.newArrayList(getEntities(DATASET, Dataset.class));
  }

  public void setDataset(List<Dataset> dataset) {
    set(DATASET, dataset);
  }

  public List<Keyword> getKeywords() {
    return Lists.newArrayList(getEntities(KEYWORDS, Keyword.class));
  }

  public void setKeywords(List<Keyword> keywords) {
    set(KEYWORDS, keywords);
  }

  public Provider getProvider() {
    return getEntity(PROVIDER, Provider.class);
  }

  public void setProvider(Provider provider) {
    set(PROVIDER, provider);
  }

  public String getBioschemaString() {
    return this.getClass().getSimpleName() + "_TODO_";
  }
}

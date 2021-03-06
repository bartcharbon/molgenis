package org.molgenis.data;

import static java.util.Objects.requireNonNull;

import org.molgenis.data.meta.model.EntityType;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class UnknownSortAttributeException extends UnknownDataException {
  private static final String ERROR_CODE = "D27";
  private final transient EntityType entityType;
  private final String attributeName;

  public UnknownSortAttributeException(EntityType entityType, String attributeName) {
    super(ERROR_CODE);
    this.entityType = requireNonNull(entityType);
    this.attributeName = requireNonNull(attributeName);
  }

  @Override
  public String getMessage() {
    return "type:" + entityType.getId() + " attribute:" + attributeName;
  }

  @Override
  protected Object[] getLocalizedMessageArguments() {
    return new Object[] {attributeName};
  }
}

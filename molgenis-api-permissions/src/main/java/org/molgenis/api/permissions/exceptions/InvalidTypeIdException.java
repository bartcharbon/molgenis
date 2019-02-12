package org.molgenis.api.permissions.exceptions;

import static java.util.Objects.requireNonNull;

import org.molgenis.i18n.CodedRuntimeException;

public class InvalidTypeIdException extends CodedRuntimeException {
  private static final String ERROR_CODE = "PRM05";

  private final String typeId;

  public InvalidTypeIdException(String typeId) {
    super(ERROR_CODE);
    this.typeId = requireNonNull(typeId);
  }

  @Override
  public String getMessage() {
    return String.format("typeId:%s", typeId);
  }

  @Override
  protected Object[] getLocalizedMessageArguments() {
    return new Object[] {typeId};
  }
}

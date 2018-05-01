package org.molgenis.data.security.exception;

import org.molgenis.data.meta.model.Package;
import org.molgenis.data.security.PackagePermission;

import static java.util.Objects.requireNonNull;

public class PackagePermissionDeniedException extends PermissionDeniedException {
  private static final String ERROR_CODE = "DS01";
  private final PackagePermission permission;
  private final transient Package pack;

  public PackagePermissionDeniedException(PackagePermission permission, Package pack) {
    super(ERROR_CODE);
    this.permission = requireNonNull(permission);
    this.pack = requireNonNull(pack);
  }

  @Override
  protected Object[] getLocalizedMessageArguments() {
    return new Object[] {permission.getName(), pack.getLabel()};
  }
}

package org.molgenis.dataexplorer.controller.bioschema.model.meta;

import static java.util.Objects.requireNonNull;
import static org.molgenis.data.system.model.RootSystemPackage.PACKAGE_SYSTEM;

import org.molgenis.data.meta.SystemPackage;
import org.molgenis.data.meta.model.PackageMetadata;
import org.molgenis.data.system.model.RootSystemPackage;
import org.springframework.stereotype.Component;

@Component
public class BioschemaPackage extends SystemPackage {
  public static final String SIMPLE_NAME = "bioschema";
  public static final String PACKAGE_BIOSCHEMA = PACKAGE_SYSTEM + PACKAGE_SEPARATOR + SIMPLE_NAME;

  private final RootSystemPackage rootSystemPackage;

  public BioschemaPackage(PackageMetadata packageMetadata, RootSystemPackage rootSystemPackage) {
    super(PACKAGE_BIOSCHEMA, packageMetadata);
    this.rootSystemPackage = requireNonNull(rootSystemPackage);
  }

  @Override
  protected void init() {
    setLabel("Bioschema");
    setDescription("Package containing bioschema related entities");
    setParent(rootSystemPackage);
  }
}

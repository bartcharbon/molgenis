package org.molgenis.dataexplorer.controller.bioschema.model.meta;

import static java.util.Objects.requireNonNull;
import static org.molgenis.data.meta.model.EntityType.AttributeRole.ROLE_ID;
import static org.molgenis.data.meta.model.Package.PACKAGE_SEPARATOR;
import static org.molgenis.dataexplorer.controller.bioschema.model.meta.BioschemaPackage.PACKAGE_BIOSCHEMA;

import org.molgenis.data.meta.AttributeType;
import org.molgenis.data.meta.SystemEntityType;
import org.springframework.stereotype.Component;

@Component
public class KeywordMetadata extends SystemEntityType {
  private static final String SIMPLE_NAME = "keyword";
  public static final String SCHEMA = PACKAGE_BIOSCHEMA + PACKAGE_SEPARATOR + SIMPLE_NAME;

  public static final String KEYWORDD = "keyword";

  private final BioschemaPackage bioschemaPackage;

  public KeywordMetadata(BioschemaPackage bioschemaPackage) {
    super(SIMPLE_NAME, PACKAGE_BIOSCHEMA);
    this.bioschemaPackage = requireNonNull(bioschemaPackage);
  }

  @Override
  public void init() {
    setLabel("Bioschema Keyword");
    setPackage(bioschemaPackage);

    setDescription("Keyword");

    addAttribute(KEYWORDD, ROLE_ID).setDataType(AttributeType.STRING);
  }
}

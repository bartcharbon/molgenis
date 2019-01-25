package org.molgenis.dataexplorer.controller.bioschema.model.meta;

import static java.util.Objects.requireNonNull;
import static org.molgenis.data.meta.model.EntityType.AttributeRole.ROLE_ID;
import static org.molgenis.data.meta.model.Package.PACKAGE_SEPARATOR;
import static org.molgenis.dataexplorer.controller.bioschema.model.meta.BioschemaPackage.PACKAGE_BIOSCHEMA;

import org.molgenis.data.meta.AttributeType;
import org.molgenis.data.meta.SystemEntityType;
import org.molgenis.dataexplorer.controller.bioschema.model.enums.Type;
import org.springframework.stereotype.Component;

@Component
public class VariableMeassuredMetadata extends SystemEntityType {
  private static final String SIMPLE_NAME = "variableMeassured";
  public static final String SCHEMA = PACKAGE_BIOSCHEMA + PACKAGE_SEPARATOR + SIMPLE_NAME;

  public static final String NAME = "name";
  public static final String TYPE = "type";
  public static final String URL = "url";
  private final BioschemaPackage bioschemaPackage;

  public VariableMeassuredMetadata(BioschemaPackage bioschemaPackage) {
    super(SIMPLE_NAME, PACKAGE_BIOSCHEMA);
    this.bioschemaPackage = requireNonNull(bioschemaPackage);
  }

  @Override
  public void init() {
    setLabel("Bioschema Variable Meassured");
    setPackage(bioschemaPackage);

    setDescription("Variable Meassured");

    addAttribute(NAME, ROLE_ID).setDataType(AttributeType.STRING);
    addAttribute(URL).setDataType(AttributeType.HYPERLINK);
    addAttribute(TYPE).setDataType(AttributeType.ENUM).setEnumOptions(Type.getOptionsLowercase());
  }
}

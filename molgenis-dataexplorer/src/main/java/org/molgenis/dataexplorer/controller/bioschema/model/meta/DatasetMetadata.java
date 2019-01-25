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
public class DatasetMetadata extends SystemEntityType {
  private static final String SIMPLE_NAME = "dataset";
  public static final String SCHEMA = PACKAGE_BIOSCHEMA + PACKAGE_SEPARATOR + SIMPLE_NAME;

  public static final String NAME = "name";
  public static final String TYPE = "type";
  public static final String DESCRIPTION = "description";
  public static final String IDENTIFIER = "identifier";
  public static final String URL = "url";
  public static final String KEYWORDS = "keywords";
  public static final String VARIABLESMEASSURED = "variablesMeasured";

  private final BioschemaPackage bioschemaPackage;
  private VariableMeassuredMetadata variableMeassured;

  public DatasetMetadata(
      BioschemaPackage bioschemaPackage, VariableMeassuredMetadata variableMeassured) {
    super(SIMPLE_NAME, PACKAGE_BIOSCHEMA);
    this.bioschemaPackage = requireNonNull(bioschemaPackage);
    this.variableMeassured = requireNonNull(variableMeassured);
  }

  @Override
  public void init() {
    setLabel("Bioschema Dataset");
    setPackage(bioschemaPackage);

    setDescription("Dataset");

    addAttribute(IDENTIFIER, ROLE_ID).setDataType(AttributeType.STRING);
    addAttribute(DESCRIPTION).setDataType(AttributeType.STRING);
    addAttribute(NAME).setDataType(AttributeType.STRING);
    addAttribute(URL).setDataType(AttributeType.HYPERLINK);
    addAttribute(VARIABLESMEASSURED)
        .setDataType(AttributeType.MREF)
        .setRefEntity(variableMeassured);
    addAttribute(TYPE).setDataType(AttributeType.ENUM).setEnumOptions(Type.getOptionsLowercase());
  }
}

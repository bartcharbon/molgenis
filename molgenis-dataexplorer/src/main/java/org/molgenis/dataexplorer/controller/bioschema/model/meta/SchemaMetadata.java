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
public class SchemaMetadata extends SystemEntityType {
  private static final String SIMPLE_NAME = "schema";
  public static final String SCHEMA = PACKAGE_BIOSCHEMA + PACKAGE_SEPARATOR + SIMPLE_NAME;

  public static final String CONTEXT = "context";
  public static final String TYPE = "type";
  public static final String IDENTIFIER = "identifier";
  public static final String SOURCEORGANISATION = "sourceOrganization";
  public static final String PUBLICATION = "publication";
  public static final String DESCRIPTION = "description";
  public static final String URL = "url";
  public static final String DATASET = "dataset";
  public static final String KEYWORDS = "keywords";
  public static final String PROVIDER = "provider";

  private final BioschemaPackage bioschemaPackage;
  private DatasetMetadata datasetMetadata;
  private KeywordMetadata keywordMetadata;
  private ProviderMetadata providerMetadata;
  private PublicationEventMetadata publicationEventMetadata;

  public SchemaMetadata(
      BioschemaPackage bioschemaPackage,
      DatasetMetadata datasetMetadata,
      KeywordMetadata keywordMetadata,
      ProviderMetadata providerMetadata,
      PublicationEventMetadata publicationEventMetadata) {
    super(SIMPLE_NAME, PACKAGE_BIOSCHEMA);
    this.bioschemaPackage = requireNonNull(bioschemaPackage);
    this.datasetMetadata = requireNonNull(datasetMetadata);
    this.keywordMetadata = requireNonNull(keywordMetadata);
    this.providerMetadata = requireNonNull(providerMetadata);
    this.publicationEventMetadata = requireNonNull(publicationEventMetadata);
  }

  @Override
  public void init() {
    setLabel("Bioschema Schema");
    setPackage(bioschemaPackage);

    setDescription("Schema");

    addAttribute(IDENTIFIER, ROLE_ID).setDataType(AttributeType.STRING);
    addAttribute(CONTEXT).setDataType(AttributeType.STRING);
    addAttribute(TYPE).setDataType(AttributeType.ENUM).setEnumOptions(Type.getOptionsLowercase());
    addAttribute(SOURCEORGANISATION).setDataType(AttributeType.STRING);
    addAttribute(PUBLICATION)
        .setDataType(AttributeType.XREF)
        .setRefEntity(publicationEventMetadata);
    addAttribute(DESCRIPTION).setDataType(AttributeType.STRING);
    addAttribute(URL).setDataType(AttributeType.STRING);
    addAttribute(DATASET).setDataType(AttributeType.XREF).setRefEntity(datasetMetadata);
    addAttribute(KEYWORDS).setDataType(AttributeType.MREF).setRefEntity(keywordMetadata);
    addAttribute(PROVIDER).setDataType(AttributeType.XREF).setRefEntity(providerMetadata);
  }
}

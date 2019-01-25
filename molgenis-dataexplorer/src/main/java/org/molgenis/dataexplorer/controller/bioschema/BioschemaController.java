package org.molgenis.dataexplorer.controller.bioschema;

import static org.molgenis.dataexplorer.controller.bioschema.BioschemaController.BASE_URI;

import org.molgenis.data.DataService;
import org.molgenis.data.Entity;
import org.molgenis.dataexplorer.controller.bioschema.model.Schema;
import org.molgenis.dataexplorer.controller.bioschema.model.meta.SchemaMetadata;
import org.molgenis.security.core.runas.RunAsSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/** Serves entity report for the molgenis BioSchema. */
@Controller
@RequestMapping(BASE_URI)
public class BioschemaController {
  private static final Logger LOG = LoggerFactory.getLogger(BioschemaController.class);

  static final String BASE_URI = "/api/bioschema";

  private final DataService dataService;

  public BioschemaController(DataService dataService) {
    this.dataService = dataService;
  }

  @GetMapping(value = "/{schemaId}")
  @ResponseBody
  @RunAsSystem
  public String getSchema(@PathVariable("schemaId") String schemaId) {
    Schema schema = dataService.findOneById(SchemaMetadata.SCHEMA, schemaId, Schema.class);
    Entity appSettings = dataService.findOneById("sys_set_app", "app");
    return "<script type=\"application/ld+json\">\n"
        + "{\n"
        + "    \"@context\": \""
        + schema.getContext()
        + "\",\n"
        + "    \"@type\": \""
        + schema.getType()
        + "\", \n"
        + "    \"name\": \""
        + appSettings.get("title")
        + "\",\n"
        + "    \"sourceOrganization\" : \""
        + schema.getSourceOrganization()
        + "\",\n"
        + "    \"publication\" :"
        + schema.getPublication()
        + "\n"
        + "    \"description\": \""
        + schema.getDescription()
        + "\"\n"
        + "    \"url\": \""
        + schema.getUrl()
        + "\",\n"
        + "    \"dataset\": [ "
        + schema.getDataset()
        + "]\n"
        + "    \"keywords\": ["
        + schema.getKeywords()
        + "]\n"
        + "    \"provider\": ["
        + schema.getProvider()
        + "]\n"
        + "     ]\n"
        + "}\n"
        + "</script>";
  }

  private String getDatasets() {
    return "    \"dataset\": [\n"
        + "        {\n"
        + "            \"@type\" : \"Dataset\",\n"
        + "            \"name\" : \"Patients\",\n"
        + "            \"description\": \"Overview of DEB patients, including their mutation and observed phenotypes.\",\n"
        + "        \"identifier\": \"deb-central:col7a1_Patients\",\n"
        + "        \"url\": \"https://molgenis42.gcc.rug.nl/menu/main/dataexplorer?entity=col7a1_Patients\",\n"
        + "        \"keywords\":[\"patients\",\"phenotypes\"],\n"
        + "        \"variableMeasured\":[\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP_0000160\",\"name\":\"Microstomia\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP_0001596\",\"name\":\"Alopecia\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0004334\",\"name\":\"Skin atrophy\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0001056\",\"name\":\"Milia\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0008404\",\"name\":\"Nail dystrophy\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:-\",\"name\":\"Albopapuloid papules\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:-\",\"name\":\"Pruritic papules\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0002860\",\"name\":\"Squamous cell carcinoma(s)\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0001371\",\"name\":\"Flexion contractures\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0004057\",\"name\":\"Pseudosyndactyly (hands)\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0010296\",\"name\":\"Ankyloglossia\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0002015\",\"name\":\"Dysphagia\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0001510\",\"name\":\"Growth retardation\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0001903\",\"name\":\"Anaemia\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0000083\",\"name\":\"Renal failure\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0001644\",\"name\":\"Dilated cardiomyopathy\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:0008066\",\"name\":\"Blistering\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:-\",\"name\":\"Proximal body flexures\"},\n"
        + "              { \"@type\" : \"PropertyValue\", \"url\":\"http://purl.obolibrary.org/obo/HP:-\",\"name\":\"Mucosa\"}\n"
        + "            ]\n"
        + "            },\n"
        + "           {\n"
        + "            \"@type\" : \"Dataset\",\n"
        + "            \"name\" : \"Mutations\",\n"
        + "            \"description\": \"Overview and genomic annotations of COL7A1 mutations found in DEB patients.\",\n"
        + "            \"identifier\": \"deb-central:col7a1_Mutations\",\n"
        + "        \"url\": \"https://molgenis42.gcc.rug.nl/menu/main/dataexplorer?entity=col7a1_Mutations\",\n"
        + "        \"keywords\": [\"mutations\",\"genomics\",\"col7a1\"]\n"
        + "           },\n"
        + "           {\n"
        + "            \"@type\" : \"Dataset\",\n"
        + "            \"name\" : \"References\",\n"
        + "            \"description\": \"Literature used in the creation of the DEB Register mutation and patient overviews.\",\n"
        + "            \"identifier\": \"deb-central:col7a1_Publications\",\n"
        + "        \"url\": \"https://molgenis42.gcc.rug.nl/menu/main/dataexplorer?entity=col7a1_Publications\",\n"
        + "        \"keywords\": [\"literature\",\"references\"]\n"
        + "           }\n"
        + "        ],\n";
  }
}

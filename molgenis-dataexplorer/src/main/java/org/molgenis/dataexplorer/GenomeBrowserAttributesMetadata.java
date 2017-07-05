package org.molgenis.dataexplorer;

import org.molgenis.data.meta.AttributeType;
import org.molgenis.data.meta.SystemEntityType;
import org.molgenis.data.meta.model.AttributeMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;
import static org.molgenis.data.meta.model.EntityType.AttributeRole.ROLE_ID;
import static org.molgenis.dataexplorer.GenomeBrowserPackage.PACKAGE_GENOME_BROWSER;

@Component
public class GenomeBrowserAttributesMetadata extends SystemEntityType
{
	public static final String SIMPLE_NAME = "GenomeBrowserAttributesMetadata";

	public static final String IDENTIFIER = "id";
	public static final String POS = "pos";
	public static final String CHROM = "chr";
	public static final String REF = "ref";
	public static final String ALT = "alt";
	public static final String STOP = "stop";
	public static final String DESCRIPTION = "description";
	public static final String PATIENT_ID = "patient_id";
	public static final String LINKOUT = "linkout";

	private final AttributeMetadata attributeMetadata;

	private final GenomeBrowserPackage genomeBrowserPackage;

	@Autowired
	public GenomeBrowserAttributesMetadata(GenomeBrowserPackage genomeBrowserPackage,
			AttributeMetadata attributeMetadata)
	{
		super(SIMPLE_NAME, PACKAGE_GENOME_BROWSER);
		this.genomeBrowserPackage = requireNonNull(genomeBrowserPackage);
		this.attributeMetadata = requireNonNull(attributeMetadata);
	}

	@Override
	protected void init()
	{
		setLabel("Genome Browser Attributes");
		addAttribute(IDENTIFIER, ROLE_ID).setLabel("Identifier").setAuto(true).setNillable(false);
		addAttribute(POS).setDataType(AttributeType.STRING).setRefEntity(attributeMetadata).setNillable(false);
		addAttribute(CHROM).setDataType(AttributeType.STRING).setRefEntity(attributeMetadata).setNillable(false);
		addAttribute(REF).setDataType(AttributeType.STRING);
		addAttribute(ALT).setDataType(AttributeType.STRING);
		addAttribute(STOP).setDataType(AttributeType.STRING);
		addAttribute(DESCRIPTION).setDataType(AttributeType.STRING);
		addAttribute(PATIENT_ID).setDataType(AttributeType.STRING);
		addAttribute(LINKOUT).setDataType(AttributeType.STRING);
	}
}

package org.molgenis.dataexplorer;

import org.molgenis.data.meta.SystemEntityType;
import org.molgenis.data.meta.model.AttributeMetadata;
import org.molgenis.data.meta.model.EntityTypeMetadata;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static org.molgenis.data.meta.AttributeType.*;
import static org.molgenis.data.meta.model.EntityType.AttributeRole.ROLE_ID;
import static org.molgenis.dataexplorer.GenomeBrowserPackage.PACKAGE_GENOME_BROWSER;

@Component
public class GenomeBrowserSettingsMetadata extends SystemEntityType
{
	public static final String SIMPLE_NAME = "GenomeBrowserSettingsMetadata";

	public static final String IDENTIFIER = "id";
	public static final String LABEL_ATTR = "labelAttr";
	public static final String ENTITY = "entity";
	public static final String TRACK_TYPE = "track_type";
	public static final String MOLGENIS_REFERENCE_TRACKS = "molgenis_reference_tracks";
	public static final String SHOW_ALL_MOLGENIS_REFERENCES = "show_all_references";
	public static final String GENOME_BROWSER_ATTRS = "genome_browser_attrs";
	public static final String ACTIONS = "actions";
	public static final String ADDITIONAL_REFERENCES = "additional_references";

	private EntityTypeMetadata entityTypeMetadata;
	private AttributeMetadata attributeMetadata;
	private GenomeBrowserAttributesMetadata genomeBrowserAttributesMetadata;

	private final GenomeBrowserPackage genomeBrowserPackage;

	public GenomeBrowserSettingsMetadata(GenomeBrowserPackage genomeBrowserPackage, AttributeMetadata attributeMetadata,
			EntityTypeMetadata entityTypeMetadata, GenomeBrowserAttributesMetadata genomeBrowserAttributesMetadata)
	{
		super(SIMPLE_NAME, PACKAGE_GENOME_BROWSER);
		this.genomeBrowserPackage = requireNonNull(genomeBrowserPackage);
		this.entityTypeMetadata = requireNonNull(entityTypeMetadata);
		this.attributeMetadata = requireNonNull(attributeMetadata);
		this.genomeBrowserAttributesMetadata = requireNonNull(genomeBrowserAttributesMetadata);
	}

	@Override
	protected void init()
	{
		setLabel("Genome Browser Settings");
		addAttribute(IDENTIFIER, ROLE_ID).setLabel("Identifier").setAuto(true).setNillable(false);
		addAttribute(ENTITY).setDataType(XREF).setRefEntity(entityTypeMetadata).setNillable(false);
		addAttribute(GENOME_BROWSER_ATTRS).setDataType(XREF).setRefEntity(genomeBrowserAttributesMetadata)
				.setNillable(false);
		addAttribute(TRACK_TYPE).setDataType(ENUM).setEnumOptions(Arrays.asList("variant")).setNillable(false);
		addAttribute(SHOW_ALL_MOLGENIS_REFERENCES).setDataType(BOOL).setNillable(false);
		addAttribute(LABEL_ATTR).setDataType(XREF).setRefEntity(attributeMetadata).setNillable(false);
		addAttribute(MOLGENIS_REFERENCE_TRACKS).setDataType(MREF).setRefEntity(this);
		addAttribute(ADDITIONAL_REFERENCES).setDataType(TEXT);
		addAttribute(ACTIONS).setDataType(TEXT);
	}
}

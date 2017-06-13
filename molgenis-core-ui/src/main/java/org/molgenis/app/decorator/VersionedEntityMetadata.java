package org.molgenis.data.meta.model;

import org.molgenis.data.meta.SystemEntityType;
import org.springframework.stereotype.Component;

import static org.molgenis.data.meta.AttributeType.STRING;

@Component
public class VersionedEntityMetadata extends SystemEntityType
{
	public VersionedEntityMetadata()
	{
		super("versioned", "sys");
	}

	@Override
	public void init()
	{

		setLabel("blaat");
		setPackage(null);

		setAbstract(true);
		addAttribute("123").setDataType(STRING).setVisible(false);
	}
}

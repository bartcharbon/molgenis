package org.molgenis.app.decorator;

import org.molgenis.auth.SecurityPackage;
import org.molgenis.auth.UserMetaData;
import org.molgenis.data.meta.SystemEntityType;
import org.molgenis.data.meta.model.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static org.molgenis.auth.SecurityPackage.PACKAGE_SECURITY;
import static org.molgenis.data.meta.AttributeType.*;

@Component
public class VersionedEntityType extends SystemEntityType
{
	private final SecurityPackage securityPackage;

	public VersionedEntityType(SecurityPackage securityPackage)
	{
		super("versioned", PACKAGE_SECURITY);
		this.securityPackage = requireNonNull(securityPackage);
	}

	@Autowired
	UserMetaData userMetaData;

	@Override
	public void init()
	{
		setLabel("Versioned entity");

		setAbstract(true);
		Attribute compound = addAttribute("history").setLabel("History").setDataType(COMPOUND);
		addAttribute("oldRowId").setLabel("Archived Row ID").setDataType(STRING).setVisible(false);
		addAttribute("rowId").setLabel("Row ID").setDataType(STRING).setVisible(false);
		addAttribute("changeDate").setLabel("Change Date").setDataType(DATE_TIME).setParent(compound);
		addAttribute("changeType").setLabel("Change Type").setDataType(ENUM)
				.setEnumOptions(Arrays.asList("add", "update", "delete")).setParent(compound);
		addAttribute("changeInfo").setLabel("Change Information").setParent(compound);
		addAttribute("User").setLabel("User").setDataType(XREF).setRefEntity(userMetaData).setParent(compound);
		addAttribute("ID").setLabel("ID").setDataType(STRING).setVisible(false).setIdAttribute(true).setNillable(false);
	}
}

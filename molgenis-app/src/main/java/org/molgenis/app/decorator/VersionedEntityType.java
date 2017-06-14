package org.molgenis.app.decorator;

import org.molgenis.auth.UserMetaData;
import org.molgenis.data.meta.SystemEntityType;
import org.molgenis.data.meta.model.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.molgenis.data.meta.AttributeType.*;

@Component
public class VersionedEntityType extends SystemEntityType
{
	public static final String HISTORY = "history";
	public static final String OLD_ROW_ID = "oldRowId";
	public static final String ROW_ID = "rowId";
	public static final String CHANGE_DATE = "changeDate";
	public static final String CHANGE_TYPE = "changeType";
	public static final String CHANGE_INFO = "changeInfo";
	public static final String USER = "user";
	public static final String DELETED = "deleted";
	public static final String ID = "ID";
	public static final String HISTORY_SELF_REFERENCE = "HistorySelfReference";
	public static final String ADD = "add";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";

	public VersionedEntityType()
	{
		super("versioned", "sys_version");
	}

	@Autowired
	UserMetaData userMetaData;

	@Override
	public void init()
	{
		setLabel("Versioned entity");

		setAbstract(true);
		Attribute compound = addAttribute(HISTORY).setLabel("History").setDataType(COMPOUND);
		addAttribute(OLD_ROW_ID).setLabel("Archived Row ID").setDataType(STRING).setVisible(false);
		addAttribute(ROW_ID).setLabel("Row ID").setDataType(STRING).setVisible(false);
		addAttribute(CHANGE_DATE).setLabel("Change Date").setDataType(DATE_TIME).setParent(compound);
		addAttribute(CHANGE_TYPE).setLabel("Change Type").setDataType(ENUM)
				.setEnumOptions(Arrays.asList(ADD, UPDATE, DELETE)).setParent(compound);
		addAttribute(CHANGE_INFO).setLabel("Change Information").setParent(compound);
		addAttribute(USER).setLabel("User").setDataType(XREF).setRefEntity(userMetaData).setParent(compound);
		addAttribute(DELETED).setLabel("Deleted").setDataType(BOOL).setVisible(false);
		addAttribute(ID).setLabel("ID").setDataType(STRING).setVisible(false).setIdAttribute(true).setNillable(false)
				.setAuto(true);
	}
}

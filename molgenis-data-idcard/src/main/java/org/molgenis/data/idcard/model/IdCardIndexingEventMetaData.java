package org.molgenis.data.idcard.model;

import static org.molgenis.MolgenisFieldTypes.DATETIME;
import static org.molgenis.MolgenisFieldTypes.TEXT;
import static org.molgenis.data.meta.EntityMetaData.AttributeRole.ROLE_ID;
import static org.molgenis.data.meta.EntityMetaData.AttributeRole.ROLE_LABEL;
import static org.molgenis.data.meta.EntityMetaData.AttributeRole.ROLE_LOOKUP;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.molgenis.data.meta.EntityMetaDataImpl;
import org.molgenis.data.meta.SystemEntityMetaDataImpl;
import org.molgenis.fieldtypes.EnumField;
import org.springframework.stereotype.Component;

@Component
public class IdCardIndexingEventMetaData extends SystemEntityMetaDataImpl
{
	@Override
	public void init()
	{
		setName(IdCardIndexingEvent.ENTITY_NAME);
		addAttribute(IdCardIndexingEvent.ID, ROLE_ID).setVisible(false).setAuto(true).setLabel("Id");
		addAttribute(IdCardIndexingEvent.DATE).setDataType(DATETIME).setNillable(false).setAuto(true).setLabel("Date");
		addAttribute(IdCardIndexingEvent.STATUS, ROLE_LABEL, ROLE_LOOKUP)
				.setDataType(new EnumField()).setEnumOptions(Arrays.stream(IdCardIndexingEventStatus.values())
						.map(value -> value.toString()).collect(Collectors.toList()))
				.setNillable(false).setLabel("Status");
		addAttribute(IdCardIndexingEvent.MESSAGE, ROLE_LOOKUP).setDataType(TEXT).setNillable(true).setLabel("Message");
	}
}

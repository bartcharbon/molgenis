package org.molgenis.data.elasticsearch.logback;

import static org.molgenis.data.meta.EntityMetaData.AttributeRole.ROLE_ID;

import org.molgenis.MolgenisFieldTypes;
import org.molgenis.data.elasticsearch.ElasticsearchRepositoryCollection;
import org.molgenis.data.meta.SystemEntityMetaDataImpl;
import org.springframework.stereotype.Component;

@Component
public class LoggingEventMetaData extends SystemEntityMetaDataImpl
{
	private static final String ENTITY_NAME = "LoggingEvent";
	public static final String IDENTIFIER = "identifier";
	public static final String THREAD = "thread";
	public static final String LEVEL = "level";
	public static final String LOGGER = "logger";
	public static final String MESSAGE = "message";
	public static final String TIMESTAMP = "timestamp";
	public static final String STACKTRACE = "stacktrace";

	@Override
	public void init()
	{
		setName(ENTITY_NAME);
		setBackend(ElasticsearchRepositoryCollection.NAME);
		addAttribute(IDENTIFIER, ROLE_ID).setVisible(false);
		addAttribute(TIMESTAMP).setDataType(MolgenisFieldTypes.DATETIME);
		addAttribute(THREAD);
		addAttribute(LEVEL);
		addAttribute(LOGGER);
		addAttribute(MESSAGE).setDataType(MolgenisFieldTypes.TEXT);
		addAttribute(STACKTRACE).setDataType(MolgenisFieldTypes.TEXT);
	}
}

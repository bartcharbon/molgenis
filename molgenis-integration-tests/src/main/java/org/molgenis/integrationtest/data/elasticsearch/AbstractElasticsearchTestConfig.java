package org.molgenis.integrationtest.data.elasticsearch;

import org.molgenis.DatabaseConfig;
import org.molgenis.data.ManageableRepositoryCollection;
import org.molgenis.data.elasticsearch.ElasticsearchRepositoryCollection;
import org.molgenis.integrationtest.data.AbstractDataApiTestConfig;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public abstract class AbstractElasticsearchTestConfig extends AbstractDataApiTestConfig
{
	@Override
	protected ManageableRepositoryCollection getBackend()
	{
		return elasticsearchRepositoryCollection();
	}

	@Bean
	public ElasticsearchRepositoryCollection elasticsearchRepositoryCollection()
	{
		return new ElasticsearchRepositoryCollection(searchService, dataService());
	}

	@Bean
	public DatabaseConfig databaseConfig()
	{
		return new DatabaseConfig();
	}

	@Override
	public DataSource dataSource() {
		return databaseConfig().dataSource();
	}
}

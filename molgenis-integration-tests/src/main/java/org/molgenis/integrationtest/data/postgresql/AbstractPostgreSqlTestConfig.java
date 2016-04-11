package org.molgenis.integrationtest.data.postgresql;

import org.molgenis.DatabaseConfig;
import org.molgenis.data.DataService;
import org.molgenis.data.ManageableRepositoryCollection;
import org.molgenis.data.postgresql.PostgreSqlEntityFactory;
import org.molgenis.data.postgresql.PostgreSqlRepository;
import org.molgenis.data.postgresql.PostgreSqlRepositoryCollection;
import org.molgenis.integrationtest.data.AbstractDataApiTestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.testng.annotations.AfterClass;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Import(
{ PostgreSqlEntityFactory.class })
public abstract class AbstractPostgreSqlTestConfig extends AbstractDataApiTestConfig
{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractPostgreSqlTestConfig.class);

	@Autowired
	DataService dataService;

	@Autowired
	PostgreSqlEntityFactory postgreSqlEntityFactory;

	@Override
	protected ManageableRepositoryCollection getBackend()
	{
		return new PostgreSqlRepositoryCollection()
		{
			@Override
			protected PostgreSqlRepository createPostgreSqlRepository()
			{
				return new PostgreSqlRepository(dataService, postgreSqlEntityFactory, dataSource());
			}

			@Override
			public boolean hasRepository(String name)
			{
				throw new UnsupportedOperationException();
			}
		};
	}

	@PostConstruct
	public void init()
	{
		// FIXME: get connection props from molgenis.properties
		try
		{
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/molgenis", "molgenis",
					"molgenis");
			conn.createStatement()
					.execute("drop schema if exists \"integrationtest\" cascade ;create schema \"integrationtest\";");
			conn.close();
		}
		catch (SQLException e)
		{
			LOG.error("Error creating a clean 'integrationtest' schema to run the integration test on");
			LOG.error(
					"This test needs a PostgreSql database with the name specified in the molgenis.properties of this module.");
			throw new RuntimeException(e.getMessage());
		}
		super.init();
	}

	@AfterClass
	public void cleanup()
	{
		// FIXME: get connection props from molgenis.properties
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/molgenis", "molgenis", "molgenis");
			conn.createStatement().execute("drop schema if exists \"integrationtest\" cascade;");
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Bean
	public DatabaseConfig databaseConfig()
	{
		return new DatabaseConfig();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties()
	{
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new Resource[]
				{ new FileSystemResource(System.getProperty("molgenis.home") + "/molgenis-server.properties"),
						new ClassPathResource("/postgresql/molgenis.properties") };
		pspc.setLocations(resources);
		pspc.setFileEncoding("UTF-8");
		pspc.setIgnoreUnresolvablePlaceholders(true);
		pspc.setIgnoreResourceNotFound(true);
		pspc.setNullValue("@null");
		return pspc;
	}

	@Override
	public DataSource dataSource()
	{
		return databaseConfig().dataSource();
	}

}

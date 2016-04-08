package org.molgenis.integrationtest.data.postgresql;

import org.molgenis.DatabaseConfig;
import org.molgenis.data.DataService;
import org.molgenis.data.ManageableRepositoryCollection;
import org.molgenis.data.postgresql.PostgreSqlEntityFactory;
import org.molgenis.data.postgresql.PostgreSqlRepository;
import org.molgenis.data.postgresql.PostgreSqlRepositoryCollection;
import org.molgenis.integrationtest.data.AbstractDataApiTestConfig;
import org.molgenis.integrationtest.data.SecuritySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;

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
        //You can specify the url like so:
        //jdbc:postgresql://localhost:5432/mydatabase?searchpath=myschema
        //jdbc:postgresql://localhost:5432/mydatabase?currentSchema=myschema
        //FIXME: use molgenis database but with integrationtest schema?

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/molgenisIT", "molgenis", "molgenis");
            conn.createStatement().execute("drop schema if exists public cascade ;create schema public;");
            conn.close();
        } catch (SQLException e) {
            LOG.error("Error creating a clean 'default' schema to run the integration test on");
            LOG.error("This test needs a PostgreSql database with the name specified in the molgenis.properties of this module.");
            LOG.error("This database needs to have a schema 'default', both the database and the schema should belong to the molgenis user.");
            throw new RuntimeException(e.getMessage());
        }
        super.init();
    }

    @PreDestroy
    public void cleanup()
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/molgenisIT", "molgenis", "molgenis");
            conn.createStatement().execute("drop schema if exists public cascade ;create schema public;");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	@Bean
	public DatabaseConfig databaseConfig()
	{
		return new DatabaseConfig();
	}

	@Override
	public DataSource dataSource()
	{
		return databaseConfig().dataSource();
	}

}

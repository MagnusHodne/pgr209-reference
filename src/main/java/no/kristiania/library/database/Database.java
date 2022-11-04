package no.kristiania.library.database;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    public static DataSource getDataSource() {
        var properties = new Properties();
        try {
            properties.load(new FileReader("application.properties"));
        } catch (IOException e) {
            logger.error("Unable to load application.properties");
            throw new RuntimeException(e);
        }

        try(var dataSource = new HikariDataSource()) {
            dataSource.setJdbcUrl(properties.getProperty("jdbc.url"));
            dataSource.setUsername(properties.getProperty("jdbc.username"));
            dataSource.setPassword(properties.getProperty("jdbc.password"));
            Flyway.configure().dataSource(dataSource).load().migrate();
            return dataSource;
        } catch (FlywayException e) {
            logger.error("Unable to migrate database", e);
            throw new RuntimeException(e);
        }
    }
}

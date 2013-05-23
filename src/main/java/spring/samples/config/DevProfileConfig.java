package spring.samples.config;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author : tsaltsol
 * Date: 08.05.13
 */
@Configuration
@Profile("dev")
public class DevProfileConfig {

    @Bean(destroyMethod = "shutdown")
    public DataSource dataSource() {
        EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();
        factory.setDatabaseType(EmbeddedDatabaseType.HSQL);
        return initializeDatabase(factory.getDatabase());
    }

    private DataSource initializeDatabase(DataSource database) {
        String ddl, dml;
        try {
            ddl = IOUtils.toString(new ClassPathResource("db/schema.sql").getInputStream());
            dml = IOUtils.toString(new ClassPathResource("db/test-data.sql").getInputStream());
        } catch (IOException ex) {
            throw new RuntimeException("Can't read database init scripts from classpath", ex);
        }

        try(Statement statement = database.getConnection().createStatement()){
            statement.addBatch(ddl);
            statement.addBatch(dml);
            statement.executeBatch();
            return database;
        } catch (SQLException ex) {
            throw new RuntimeException("Database exception has been thrown during initialization: "+ex);
        }
    }
}

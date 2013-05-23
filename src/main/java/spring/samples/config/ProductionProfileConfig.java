package spring.samples.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * @author : tsaltsol
 * Date: 08.05.13
 */
@Configuration
@PropertySource("classpath:prod-database.properties")
@Profile("production")
public class ProductionProfileConfig {

    @Inject
    private Environment environment;

    @Bean(name="dataSource", destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl(environment.getProperty("url"));
        dataSource.setUsername(environment.getProperty("user"));
        dataSource.setPassword(environment.getProperty("password"));
        return dataSource;
    }
}

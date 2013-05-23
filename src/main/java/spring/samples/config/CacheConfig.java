package spring.samples.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author : tsaltsol
 * Date: 19.05.13
 */

@Configuration
@EnableCaching
public class CacheConfig {

    @Inject
    private DataSource dataSource;

    @Bean
    public CacheManager cacheManager(){
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("books")));
        return cacheManager;
    }

    @Configuration
    static class DevProfileConfig {
        @Bean(destroyMethod = "shutdown")
        public DataSource dataSource() {
            EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();
            factory.setDatabaseType(EmbeddedDatabaseType.HSQL);
            return factory.getDatabase();
        }
    }
}

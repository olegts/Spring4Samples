package spring.samples.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import spring.samples.dao.BookStoreDao;
import spring.samples.service.BookStoreService;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author : tsaltsol
 * Date: 08.05.13
 */
@Configuration
//@EnableCaching
@PropertySource("classpath:app-common.properties")//Was not managed to set active profile from properties
@Import( { DevProfileConfig.class, ProductionProfileConfig.class } )
public class BookStoreAppConfig {

    @Inject
    private DataSource dataSource;

    @Bean
    public BookStoreService bookStoreService(){
        return new BookStoreService(bookStoreDao());
    }

    @Bean
    public BookStoreDao bookStoreDao(){
        BookStoreDao bookStoreDao = new BookStoreDao();
        bookStoreDao.setDataSource(dataSource);
        return bookStoreDao;
    }

    @Bean
    public CacheManager cacheManager(){
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("books")));
        return cacheManager;
    }

}

package spring.samples;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.samples.config.CacheConfig;

/**
 * @author : tsaltsol
 * Date: 19.05.13
 */

public class CacheConfigLauncher {
    public static void main(String args[]){
        ApplicationContext springAppContext = new AnnotationConfigApplicationContext(CacheConfig.class);
    }
}

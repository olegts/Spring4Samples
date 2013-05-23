package spring.samples;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.samples.config.BookStoreAppConfig;

/**
 * @author : tsaltsol
 * Date: 08.05.13
 */
public class JavaBasedConfigLauncher {

    private static final Logger LOG = Logger.getLogger(JavaBasedConfigLauncher.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.register(BookStoreAppConfig.class);
        context.refresh();
        /*ApplicationContext context = new AnnotationConfigApplicationContext(BookStoreAppConfig.class);*/
        LOG.info("-------------------Application context has been created successfully!!!-------------------");
    }
}

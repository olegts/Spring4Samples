package spring.samples;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import spring.samples.domain.Book;
import spring.samples.domain.ISBN;
import spring.samples.service.BookStoreService;

/**
 * @author : tsaltsol
 * Date: 02.05.13
 */
public class XMLBasedConfigLauncher {

    private static final Logger LOG = Logger.getLogger(XMLBasedConfigLauncher.class);

    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.load("classpath:bookstore-app-context.xml");
        context.refresh();
        /*ApplicationContext context = new ClassPathXmlApplicationContext("bookstore-app-context.xml");*/

        LOG.info("-------------------Application context has been created successfully!!!-------------------");

        BookStoreService bookStoreService = context.getBean(BookStoreService.class);
        Book newBook = new Book(new ISBN(123), "Clean Code", "Uncle Bob");
        LOG.info("Add book: "+newBook);
        bookStoreService.addBook(newBook);
        Book persistedBook = bookStoreService.findBook(newBook.getIsbn());
        Book defaultBook = bookStoreService.findBook(new ISBN(123456789));
        LOG.info("Found book: "+persistedBook);
        LOG.info("Found book: "+defaultBook);        
    }
}

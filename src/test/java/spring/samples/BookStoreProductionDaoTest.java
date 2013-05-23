package spring.samples;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import spring.samples.config.BookStoreAppConfig;
import spring.samples.dao.BookStoreDao;
import spring.samples.domain.Book;
import spring.samples.domain.ISBN;

import javax.sql.DataSource;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author : tsaltsol
 * Date: 11.05.13
 */
@RunWith(SpringJUnit4ClassRunner.class)
/*@ContextConfiguration("/bookstore-app-context.xml")*/
@ContextConfiguration(
        loader=AnnotationConfigContextLoader.class,
        classes={BookStoreAppConfig.class})
@ActiveProfiles("production")
public class BookStoreProductionDaoTest {
    
    private static final String CLEAN_DB_QUERY = "DELETE FROM bookstore";
    
    @Autowired
    private BookStoreDao bookStoreDao;

    private JdbcTemplate jdbcTemplate;

    @Test
    public void testProductionDao() throws Exception {
        Book book = new Book(new ISBN(123), "Clean Code", "Uncle Bob");
        //Add new book
        bookStoreDao.saveBook(book);
        //Find books by id
        assertThat(bookStoreDao.findBookBy(new ISBN(123)), is(book));
        //Find book by author
        List<Book> books = bookStoreDao.findBooksBy("Uncle Bob");
        assertThat(books.size(), is(1));
        assertThat(books.get(0), is(book));
    }

    @After
    public void cleanDatabaseAfterTest() throws Exception {
        jdbcTemplate.update(CLEAN_DB_QUERY);
    }

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}

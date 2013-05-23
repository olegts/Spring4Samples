package spring.samples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.samples.dao.BookStoreDao;
import spring.samples.domain.Book;
import spring.samples.domain.ISBN;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author : tsaltsol
 * Date: 06.05.13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/bookstore-app-context.xml")
//@ProfileValueSourceConfiguration(SystemProfileValueSource.class)
@ActiveProfiles("dev")
public class BookStoreDaoTest {

    @Autowired
    private BookStoreDao bookStoreDao;

    @Test
    public void testBookStoreDao() throws Exception {
        //Check that 1 predefined book presents in book store
        assertNotNull(bookStoreDao.findBookBy(new ISBN(123456789)));
        //Remove it
        bookStoreDao.removeBook(new ISBN(123456789));
        //Check that book is vanished
        try{
            bookStoreDao.findBookBy(new ISBN(123456789));
            fail();
        }
        catch(EmptyResultDataAccessException ex){}
        //Add 2 new books by same author
        bookStoreDao.saveBook(new Book(new ISBN(123), "Clean Code", "Uncle Bob"));
        bookStoreDao.saveBook(new Book(new ISBN(321), "Clean Coder", "Uncle Bob"));
        //Find books by author
        List<Book> books = bookStoreDao.findBooksBy("Uncle Bob");
        //Check that search gave exactly 2 books as expected
        assertThat(books.size(), is(2));
    }
}

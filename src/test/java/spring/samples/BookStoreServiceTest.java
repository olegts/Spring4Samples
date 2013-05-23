package spring.samples;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.samples.dao.BookStoreDao;
import spring.samples.domain.Book;
import spring.samples.domain.ISBN;
import spring.samples.service.BookStoreService;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author : tsaltsol
 * Date: 07.05.13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/bookstore-test-context.xml")
@ActiveProfiles("dev")
public class BookStoreServiceTest {

    private static final ISBN BOOK_ISBN = new ISBN(777);
    private static final String BOOK_AUTHOR = "Uncle Bob";
    private static final Book TEST_BOOK = new Book(BOOK_ISBN, "Clean Code", BOOK_AUTHOR);
    
    @Autowired
    private BookStoreService bookStoreService;
    @Autowired
    private BookStoreDao bookStoreDaoMock;
    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testPutInCache() throws Exception {
        //Add bestseller book in book store
        bookStoreService.addBestsellerBook(TEST_BOOK);
        //Check that it presents in book store
        assertThat(bookStoreService.findBook(BOOK_ISBN), is(TEST_BOOK));
        //Check that it has been added into cache at once
        verify(bookStoreDaoMock, never()).findBookBy(BOOK_ISBN);
    }

    @Test
    public void testGetFromCache(){
        //Add book in book store
        when(bookStoreDaoMock.findBookBy(BOOK_ISBN)).thenReturn(TEST_BOOK);
        //Search for it twice
        assertThat(bookStoreService.findBook(BOOK_ISBN), is(TEST_BOOK));
        assertThat(bookStoreService.findBook(BOOK_ISBN), is(TEST_BOOK));
        //Check that second time it has been got from cache
        verify(bookStoreDaoMock, times(1)).findBookBy(BOOK_ISBN);
    }

    @Test
    public void testCacheEviction() throws Exception {
        //Add bestseller book in book store such as it will be implicitly cached
        bookStoreService.addBestsellerBook(TEST_BOOK);
        //Reimport library such as cache should be cleared
        bookStoreService.importLibrary(Arrays.asList(TEST_BOOK));
        when(bookStoreDaoMock.findBookBy(BOOK_ISBN)).thenReturn(TEST_BOOK);
        //Search for bestseller book that has been reimported
        assertThat(bookStoreService.findBook(BOOK_ISBN), is(TEST_BOOK));
        //Check that it hasn't been got from cache
        verify(bookStoreDaoMock, times(1)).findBookBy(BOOK_ISBN);
    }

    @Test
    public void testUncacheableMultipleSearch() throws Exception {
        //Add bestseller book in book store such as it will be implicitly cached
        bookStoreService.addBestsellerBook(TEST_BOOK);
        when(bookStoreDaoMock.findBooksBy(BOOK_AUTHOR)).thenReturn(Arrays.asList(TEST_BOOK));
        //Perform multiple search by author
        bookStoreService.findBooks(BOOK_AUTHOR);
        //Perform multiple search by author second time
        bookStoreService.findBooks(BOOK_AUTHOR);
        //Check that multiple results have not been cached
        verify(bookStoreDaoMock, times(2)).findBooksBy(BOOK_AUTHOR);
    }

    @After
    public void resetMocks() throws Exception {
        cacheManager.getCache("books").clear();
        Mockito.reset(bookStoreDaoMock);
    }
}

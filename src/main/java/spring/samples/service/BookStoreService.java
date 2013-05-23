package spring.samples.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import spring.samples.dao.BookStoreDao;
import spring.samples.domain.Book;
import spring.samples.domain.ISBN;

import java.util.Collection;

/**
 * @author : tsaltsol
 * Date: 02.05.13
 */
public class BookStoreService {

    private BookStoreDao bookStoreDao;

    public BookStoreService(){}

    public BookStoreService(BookStoreDao bookStoreDao) {
        this.bookStoreDao = bookStoreDao;
    }

    @CachePut(value="books", key="#book.isbn.number")
    public Book addBestsellerBook(Book book) {
        bookStoreDao.saveBook(book);
        return book;
    }

    public void addBook(Book book) {
        bookStoreDao.saveBook(book);
    }

    @CacheEvict(value="books", allEntries=true)
    public void importLibrary(Collection<Book> books) {
        for (Book book : books) {
            bookStoreDao.saveBook(book);
        }
    }

    @Cacheable(value="books",key="#isbn.number")
    public Book findBook(ISBN isbn) {
        return bookStoreDao.findBookBy(isbn);
    }

    public Collection<Book> findBooks(String author) {
        return bookStoreDao.findBooksBy(author);
    }

    @CacheEvict(value="books", key="#isbn.number")
    public void removeBook(ISBN isbn) {
        bookStoreDao.removeBook(isbn);
    }
    
}

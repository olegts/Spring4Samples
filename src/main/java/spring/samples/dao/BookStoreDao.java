package spring.samples.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import spring.samples.domain.Book;
import spring.samples.domain.ISBN;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author : tsaltsol
 * Date: 02.05.13
 */
public class BookStoreDao {

    public static final String SELECT_BOOK_BY_ID = "SELECT * FROM bookstore WHERE id = ?";
    public static final String SELECT_BOOK_BY_AUTHOR = "SELECT * FROM bookstore WHERE author = ?";
    public static final String ADD_NEW_BOOK = "INSERT INTO bookstore(id,name,author) values (?,?,?)";
    public static final String REMOVE_BOOK_BY_ID = "DELETE FROM bookstore WHERE id = ?";

    private JdbcTemplate jdbcTemplate;

    public void saveBook(Book book) {
        jdbcTemplate.update(
                ADD_NEW_BOOK,
                book.getIsbn().getNumber(),
                book.getName(),
                book.getAuthor());
    }

    public Book findBookBy(ISBN isbn) {
        return jdbcTemplate.queryForObject(
            SELECT_BOOK_BY_ID,
            new RowMapper<Book>() {
                @Override
                public Book mapRow(ResultSet resultSet, int i) throws SQLException {
                    return new Book(new ISBN(resultSet.getLong("ID")),
                            resultSet.getString("NAME"), resultSet.getString("AUTHOR"));
                }
            },
            isbn.getNumber());
    }

    public List<Book> findBooksBy(String author) {
        List<Map<String,Object>> resultSet = jdbcTemplate.queryForList(SELECT_BOOK_BY_AUTHOR, author);
        return transformResultSetToBooksCollection(resultSet);
    }

    private List<Book> transformResultSetToBooksCollection(List<Map<String, Object>> resultSet) {
        List<Book> books = new LinkedList<>();
        for (Map<String,Object> resultSetItem : resultSet){
            books.add(
                new Book(new ISBN(((BigDecimal)resultSetItem.get("ID")).longValue()),
                        (String)resultSetItem.get("NAME"),
                        (String)resultSetItem.get("AUTHOR")
                )
            );
        }
        return books;
    }

    public void removeBook(ISBN isbn) {
        jdbcTemplate.update(REMOVE_BOOK_BY_ID, isbn.getNumber());
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}

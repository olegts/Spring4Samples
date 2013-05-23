package spring.samples.domain;

/**
 * @author : tsaltsol
 * Date: 02.05.13
 */
public class Book {

    private final String name;
    private final String author;
    private final ISBN isbn;

    public Book(ISBN isbn, String name, String author) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public ISBN getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return isbn != null ? isbn.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", isbn=" + isbn +
                '}';
    }
}

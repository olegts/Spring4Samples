package spring.samples.domain;

/**
 * @author : tsaltsol
 * Date: 02.05.13
 */
public class ISBN {
    private final long number;

    public ISBN(long number) {
        this.number = number;
    }

    public long getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISBN)) return false;

        ISBN isbn = (ISBN) o;

        if (number != isbn.number) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (number ^ (number >>> 32));
    }

    @Override
    public String toString() {
        return "ISBN{" +
                "number=" + number +
                '}';
    }
}

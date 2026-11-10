package edu.course.lab04;

import java.util.Objects;

/**
 * Книга. Неизменяемый класс.
 * Логическое равенство определяется только ISBN: название в equals и hashCode не участвует.
 */
public final class Book {

    private final String isbn;
    private final String title;

    public Book(String isbn, String title) {
        Objects.requireNonNull(isbn, "isbn");
        Objects.requireNonNull(title, "title");
        if (isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN не может быть пустым");
        }
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book other = (Book) o;
        return isbn.equals(other.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Book{isbn=" + isbn + ", title=" + title + "}";
    }
}

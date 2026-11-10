package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void valueIsFoundByAnotherInstanceWithSameIsbn() {
        Map<Book, String> shelves = new HashMap<>();
        Book stored = new Book("978-5-4461-0923-4", "Effective Java");
        shelves.put(stored, "A-12");

        Book lookup = new Book("978-5-4461-0923-4", "Effective Java");

        assertEquals("A-12", shelves.get(lookup));
    }

    @Test
    void booksWithDifferentIsbnAreNotEqual() {
        Book first = new Book("111", "Книга");
        Book second = new Book("222", "Книга");

        assertNotEquals(first, second);
    }

    @Test
    void titleDoesNotAffectEquality() {
        Book first = new Book("111", "Название 1");
        Book second = new Book("111", "Название 2");

        assertEquals(first, second);
    }

    @Test
    void equalBooksHaveEqualHashCodes() {
        Book first = new Book("111", "Название 1");
        Book second = new Book("111", "Название 2");

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void equalsReturnsFalseForNullAndOtherType() {
        Book book = new Book("111", "Книга");

        assertFalse(book.equals(null));
        assertFalse(book.equals("111"));
    }

    @Test
    void equalsIsReflexiveAndSymmetric() {
        Book first = new Book("111", "A");
        Book second = new Book("111", "B");

        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertTrue(second.equals(first));
    }

    @Test
    void blankIsbnAndNullsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Book("  ", "Книга"));
        assertThrows(NullPointerException.class, () -> new Book(null, "Книга"));
        assertThrows(NullPointerException.class, () -> new Book("111", null));
    }
}

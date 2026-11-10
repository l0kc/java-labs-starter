package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IssueIdTest {

    @Test
    void idsWithSameFieldsAreEqualAndHaveSameHashCode() {
        IssueId first = new IssueId("PROJ", 42);
        IssueId second = new IssueId("PROJ", 42);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentProjectCodeMakesIdsDifferent() {
        assertNotEquals(new IssueId("PROJ", 42), new IssueId("OTHER", 42));
    }

    @Test
    void differentNumberMakesIdsDifferent() {
        assertNotEquals(new IssueId("PROJ", 42), new IssueId("PROJ", 43));
    }

    @Test
    void equalsReturnsFalseForNull() {
        assertFalse(new IssueId("PROJ", 42).equals(null));
    }

    @Test
    void equalsReturnsFalseForObjectOfOtherType() {
        assertFalse(new IssueId("PROJ", 42).equals("PROJ-42"));
        assertFalse(new IssueId("PROJ", 42).equals(42));
    }

    @Test
    void equalsIsReflexiveSymmetricAndTransitive() {
        IssueId a = new IssueId("PROJ", 1);
        IssueId b = new IssueId("PROJ", 1);
        IssueId c = new IssueId("PROJ", 1);

        assertTrue(a.equals(a));
        assertTrue(a.equals(b) && b.equals(a));
        assertTrue(a.equals(b) && b.equals(c) && a.equals(c));
    }

    @Test
    void hashCodeIsStableBetweenCalls() {
        IssueId id = new IssueId("PROJ", 42);
        assertEquals(id.hashCode(), id.hashCode());
    }

    @Test
    void invalidArgumentsAreRejected() {
        assertThrows(NullPointerException.class, () -> new IssueId(null, 1));
        assertThrows(IllegalArgumentException.class, () -> new IssueId(" ", 1));
        assertThrows(IllegalArgumentException.class, () -> new IssueId("PROJ", 0));
        assertThrows(IllegalArgumentException.class, () -> new IssueId("PROJ", -5));
    }

    @Test
    void toStringShowsCodeAndNumber() {
        assertEquals("PROJ-42", new IssueId("PROJ", 42).toString());
    }
}

package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SampleIdTest {

    @Test
    void idsWithSameFieldsAreEqualAndHaveSameHashCode() {
        SampleId first = new SampleId("iris", 5);
        SampleId second = new SampleId("iris", 5);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentDatasetNameMakesIdsDifferent() {
        assertNotEquals(new SampleId("iris", 5), new SampleId("wine", 5));
    }

    @Test
    void differentIndexMakesIdsDifferent() {
        assertNotEquals(new SampleId("iris", 5), new SampleId("iris", 6));
    }

    @Test
    void equalsReturnsFalseForNull() {
        assertFalse(new SampleId("iris", 5).equals(null));
    }

    @Test
    void equalsReturnsFalseForObjectOfOtherType() {
        assertFalse(new SampleId("iris", 5).equals("iris#5"));
        assertFalse(new SampleId("iris", 5).equals(5));
    }

    @Test
    void equalsIsReflexiveSymmetricAndTransitive() {
        SampleId a = new SampleId("iris", 0);
        SampleId b = new SampleId("iris", 0);
        SampleId c = new SampleId("iris", 0);

        assertTrue(a.equals(a));
        assertTrue(a.equals(b) && b.equals(a));
        assertTrue(a.equals(b) && b.equals(c) && a.equals(c));
    }

    @Test
    void hashCodeIsStableBetweenCalls() {
        SampleId id = new SampleId("iris", 5);
        assertEquals(id.hashCode(), id.hashCode());
    }

    @Test
    void invalidArgumentsAreRejected() {
        assertThrows(NullPointerException.class, () -> new SampleId(null, 1));
        assertThrows(IllegalArgumentException.class, () -> new SampleId(" ", 1));
        assertThrows(IllegalArgumentException.class, () -> new SampleId("iris", -1));
    }

    @Test
    void toStringShowsDatasetAndIndex() {
        assertEquals("iris#5", new SampleId("iris", 5).toString());
    }
}

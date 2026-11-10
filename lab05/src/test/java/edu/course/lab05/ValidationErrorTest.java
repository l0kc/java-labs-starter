package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ValidationErrorTest {

    @Test
    void storesLineFieldAndMessage() {
        ValidationError error = new ValidationError(3, "status", "unknown status 'X'");

        assertEquals(3, error.getLineNumber());
        assertEquals("status", error.getField());
        assertEquals("unknown status 'X'", error.getMessage());
    }

    @Test
    void errorsWithSameContentAreEqual() {
        ValidationError first = new ValidationError(3, "status", "bad");
        ValidationError second = new ValidationError(3, "status", "bad");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, new ValidationError(4, "status", "bad"));
    }

    @Test
    void invalidArgumentsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new ValidationError(0, "f", "m"));
        assertThrows(NullPointerException.class, () -> new ValidationError(1, null, "m"));
        assertThrows(NullPointerException.class, () -> new ValidationError(1, "f", null));
    }
}

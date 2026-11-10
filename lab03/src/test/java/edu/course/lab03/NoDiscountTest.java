package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class NoDiscountTest {

    private static final double EPS = 1e-9;

    @Test
    void returnsOriginalPrice() {
        assertEquals(100.0, new NoDiscount().apply(100.0), EPS);
    }

    @Test
    void zeroPriceStaysZero() {
        assertEquals(0.0, new NoDiscount().apply(0.0), EPS);
    }

    @Test
    void negativePriceIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new NoDiscount().apply(-1.0));
    }
}

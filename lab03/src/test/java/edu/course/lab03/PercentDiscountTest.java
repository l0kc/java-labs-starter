package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PercentDiscountTest {

    private static final double EPS = 1e-9;

    @Test
    void twentyPercentOffHundredGivesEighty() {
        assertEquals(80.0, new PercentDiscount(20).apply(100.0), EPS);
    }

    @Test
    void zeroPercentKeepsPrice() {
        assertEquals(100.0, new PercentDiscount(0).apply(100.0), EPS);
    }

    @Test
    void hundredPercentGivesZero() {
        assertEquals(0.0, new PercentDiscount(100).apply(100.0), EPS);
    }

    @Test
    void percentBelowZeroIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(-0.1));
    }

    @Test
    void percentAboveHundredIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(100.1));
    }

    @Test
    void negativePriceIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(10).apply(-5.0));
    }
}

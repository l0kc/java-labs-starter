package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MeanCenteringNormalizationTest {

    private static final double EPS = 1e-9;
    private final NormalizationStrategy strategy = new MeanCenteringNormalization();

    @Test
    void subtractsMean() {
        double[] result = strategy.normalize(new double[] {1, 2, 3});
        assertArrayEquals(new double[] {-1.0, 0.0, 1.0}, result, EPS);
    }

    @Test
    void resultHasZeroMean() {
        double[] result = strategy.normalize(new double[] {10, 20, 40, 90});
        double sum = 0;
        for (double value : result) {
            sum += value;
        }
        assertEquals(0.0, sum / result.length, EPS);
    }

    @Test
    void doesNotModifyInput() {
        double[] input = {1, 2, 3};
        double[] result = strategy.normalize(input);
        assertArrayEquals(new double[] {1, 2, 3}, input, EPS);
        assertNotSame(input, result);
    }

    @Test
    void nullAndEmptyAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(null));
        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(new double[0]));
    }
}

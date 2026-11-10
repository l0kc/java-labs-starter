package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MinMaxNormalizationTest {

    private static final double EPS = 1e-9;
    private final NormalizationStrategy strategy = new MinMaxNormalization();

    @Test
    void scalesValuesToZeroOne() {
        double[] result = strategy.normalize(new double[] {2, 4, 6});
        assertArrayEquals(new double[] {0.0, 0.5, 1.0}, result, EPS);
    }

    @Test
    void handlesNegativeValues() {
        double[] result = strategy.normalize(new double[] {-10, 0, 10});
        assertArrayEquals(new double[] {0.0, 0.5, 1.0}, result, EPS);
    }

    @Test
    void constantVectorGivesZeros() {
        double[] result = strategy.normalize(new double[] {5, 5, 5});
        assertArrayEquals(new double[] {0.0, 0.0, 0.0}, result, EPS);
    }

    @Test
    void doesNotModifyInput() {
        double[] input = {2, 4, 6};
        double[] result = strategy.normalize(input);
        assertArrayEquals(new double[] {2, 4, 6}, input, EPS);
        assertNotSame(input, result);
    }

    @Test
    void nullAndEmptyAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(null));
        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(new double[0]));
    }
}

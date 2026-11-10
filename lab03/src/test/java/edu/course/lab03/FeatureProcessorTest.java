package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FeatureProcessorTest {

    private static final double EPS = 1e-9;

    @Test
    void usesMinMaxStrategy() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        assertArrayEquals(new double[] {0.0, 0.5, 1.0}, processor.process(new double[] {2, 4, 6}), EPS);
    }

    @Test
    void usesMeanCenteringStrategy() {
        FeatureProcessor processor = new FeatureProcessor(new MeanCenteringNormalization());
        assertArrayEquals(new double[] {-2.0, 0.0, 2.0}, processor.process(new double[] {2, 4, 6}), EPS);
    }

    @Test
    void strategyCanBeReplacedWithoutChangingProcessor() {
        // один и тот же вектор, разные стратегии -> разный результат
        double[] input = {2, 4, 6};
        NormalizationStrategy[] strategies = {new MinMaxNormalization(), new MeanCenteringNormalization()};
        double[][] expected = {{0.0, 0.5, 1.0}, {-2.0, 0.0, 2.0}};

        for (int i = 0; i < strategies.length; i++) {
            FeatureProcessor processor = new FeatureProcessor(strategies[i]);
            assertArrayEquals(expected[i], processor.process(input), EPS);
        }
        assertArrayEquals(new double[] {2, 4, 6}, input, EPS);
    }

    @Test
    void nullStrategyIsRejected() {
        assertThrows(NullPointerException.class, () -> new FeatureProcessor(null));
    }
}

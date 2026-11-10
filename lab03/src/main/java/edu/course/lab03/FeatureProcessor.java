package edu.course.lab03;

import java.util.Objects;

/**
 * Обрабатывает один вектор признаков. Конкретную стратегию нормализации не знает:
 * она приходит снаружи через конструктор.
 */
public class FeatureProcessor {

    private final NormalizationStrategy strategy;

    public FeatureProcessor(NormalizationStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy");
    }

    public double[] process(double[] vector) {
        return strategy.normalize(vector);
    }
}

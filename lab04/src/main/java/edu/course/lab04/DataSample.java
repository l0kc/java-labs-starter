package edu.course.lab04;

import java.util.Arrays;
import java.util.Objects;

/**
 * Минимальная версия наблюдения: вектор признаков и метка. Значение для SampleRegistry.
 * Массив копируется при создании и при чтении, поэтому объект неизменяемый.
 */
public final class DataSample {

    private final double[] features;
    private final String label;

    public DataSample(double[] features, String label) {
        Objects.requireNonNull(features, "features");
        Objects.requireNonNull(label, "label");
        if (features.length == 0) {
            throw new IllegalArgumentException("Вектор признаков не может быть пустым");
        }
        this.features = features.clone();
        this.label = label;
    }

    public double[] getFeatures() {
        return features.clone();
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "DataSample{features=" + Arrays.toString(features) + ", label=" + label + "}";
    }
}

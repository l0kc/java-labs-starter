package edu.course.lab03;

/**
 * Центрирование по среднему: x - mean. Среднее значение результата равно нулю.
 */
public class MeanCenteringNormalization implements NormalizationStrategy {

    @Override
    public double[] normalize(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Вектор не может быть null или пустым");
        }
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        double mean = sum / values.length;
        double[] result = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] - mean;
        }
        return result;
    }
}

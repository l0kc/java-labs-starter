package edu.course.lab03;

/**
 * Min-max нормализация: (x - min) / (max - min), результат в диапазоне [0, 1].
 * Если все значения равны (max == min), возвращается вектор из нулей.
 */
public class MinMaxNormalization implements NormalizationStrategy {

    @Override
    public double[] normalize(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Вектор не может быть null или пустым");
        }
        double min = values[0];
        double max = values[0];
        for (double value : values) {
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
        double range = max - min;
        double[] result = new double[values.length];
        if (range == 0) {
            return result;
        }
        for (int i = 0; i < values.length; i++) {
            result[i] = (values[i] - min) / range;
        }
        return result;
    }
}

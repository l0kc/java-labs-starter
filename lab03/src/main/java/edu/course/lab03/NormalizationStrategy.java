package edu.course.lab03;

/**
 * Контракт стратегии нормализации вектора признаков.
 */
public interface NormalizationStrategy {

    /**
     * @param values исходный вектор, не null и не пустой; не изменяется
     * @return новый массив с нормализованными значениями
     * @throws IllegalArgumentException если вектор null или пустой
     */
    double[] normalize(double[] values);
}

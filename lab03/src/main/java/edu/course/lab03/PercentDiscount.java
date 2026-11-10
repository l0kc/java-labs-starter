package edu.course.lab03;

/**
 * Скидка в процентах. Например, 20% для цены 100 даёт 80.
 */
public class PercentDiscount implements DiscountPolicy {

    private final double percent;

    /**
     * @param percent размер скидки в процентах, от 0 до 100 включительно
     * @throws IllegalArgumentException если процент вне диапазона [0, 100]
     */
    public PercentDiscount(double percent) {
        if (Double.isNaN(percent) || percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Процент должен быть от 0 до 100: " + percent);
        }
        this.percent = percent;
    }

    @Override
    public double apply(double price) {
        if (Double.isNaN(price) || price < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной: " + price);
        }
        return price * (100 - percent) / 100;
    }
}

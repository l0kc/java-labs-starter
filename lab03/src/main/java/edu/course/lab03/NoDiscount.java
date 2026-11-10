package edu.course.lab03;

/**
 * Политика без скидки: возвращает исходную цену.
 */
public class NoDiscount implements DiscountPolicy {

    @Override
    public double apply(double price) {
        if (Double.isNaN(price) || price < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной: " + price);
        }
        return price;
    }
}

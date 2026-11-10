package edu.course.lab03;

import java.util.Objects;

/**
 * Считает итоговую цену. Конкретную политику скидки не знает и не создаёт:
 * она приходит снаружи через конструктор.
 */
public class PriceCalculator {

    private final DiscountPolicy discountPolicy;

    public PriceCalculator(DiscountPolicy discountPolicy) {
        this.discountPolicy = Objects.requireNonNull(discountPolicy, "discountPolicy");
    }

    public double finalPrice(double price) {
        return discountPolicy.apply(price);
    }
}

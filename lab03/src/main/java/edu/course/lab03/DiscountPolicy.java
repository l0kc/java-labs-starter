package edu.course.lab03;

/**
 * Контракт скидочной политики: по исходной цене возвращает цену после скидки.
 */
public interface DiscountPolicy {

    /**
     * @param price исходная цена, не может быть отрицательной
     * @return цена после применения скидки
     * @throws IllegalArgumentException если цена отрицательная или не является числом
     */
    double apply(double price);
}

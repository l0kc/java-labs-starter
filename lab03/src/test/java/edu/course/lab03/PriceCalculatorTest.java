package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PriceCalculatorTest {

    private static final double EPS = 1e-9;

    @Test
    void usesNoDiscountPolicy() {
        PriceCalculator calculator = new PriceCalculator(new NoDiscount());
        assertEquals(100.0, calculator.finalPrice(100.0), EPS);
    }

    @Test
    void usesPercentDiscountPolicy() {
        PriceCalculator calculator = new PriceCalculator(new PercentDiscount(20));
        assertEquals(80.0, calculator.finalPrice(100.0), EPS);
    }

    @Test
    void policyCanBeReplacedWithoutChangingCalculator() {
        double price = 200.0;
        DiscountPolicy[] policies = {new NoDiscount(), new PercentDiscount(25)};
        double[] expected = {200.0, 150.0};

        for (int i = 0; i < policies.length; i++) {
            PriceCalculator calculator = new PriceCalculator(policies[i]);
            assertEquals(expected[i], calculator.finalPrice(price), EPS);
        }
    }

    @Test
    void nullPolicyIsRejected() {
        assertThrows(NullPointerException.class, () -> new PriceCalculator(null));
    }
}

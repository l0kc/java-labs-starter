package edu.course.lab06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Параметр типа переносит проверку типов на этап компиляции.
 *
 * <p>Эта строка НЕ компилируется, поэтому она оставлена только в комментарии:
 * <pre>
 *     Box&lt;String&gt; box = new Box&lt;&gt;(42);   // ошибка: int нельзя передать в Box&lt;String&gt;
 * </pre>
 */
class BoxTest {

    @Test
    void stringBoxStoresAndReplacesValue() {
        Box<String> box = new Box<>("привет");
        assertEquals("привет", box.get());

        box.set("hello");
        assertEquals("hello", box.get());
    }

    @Test
    void stringBoxReturnsStringWithoutCast() {
        Box<String> box = new Box<>("abc");
        String text = box.get(); // приведение типа не нужно
        assertEquals(3, text.length());
    }

    @Test
    void integerBoxStoresAndReplacesValue() {
        Box<Integer> box = new Box<>(10);
        int first = box.get();
        assertEquals(10, first);

        box.set(20);
        int second = box.get();
        assertEquals(20, second);
    }

    @Test
    void integerBoxValueCanBeUsedInArithmeticWithoutCast() {
        Box<Integer> box = new Box<>(41);
        int result = box.get() + 1;
        assertEquals(42, result);
    }
}

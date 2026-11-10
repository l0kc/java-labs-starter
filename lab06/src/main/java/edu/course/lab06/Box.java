package edu.course.lab06;

/**
 * Контейнер для одного значения произвольного типа T.
 * Параметр типа позволяет компилятору проверить, что в Box<String> кладут только String.
 */
public final class Box<T> {

    private T value;

    public Box(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}

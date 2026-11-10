package edu.course.lab06;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Учебный стек (LIFO) на основе ArrayList. Вершина стека — последний элемент списка,
 * поэтому все операции не сдвигают остальные элементы.
 *
 * <p>Сложность: push — O(1) амортизированно, pop, peek, size, isEmpty — O(1).
 * {@code null} запрещён.
 *
 * @param <T> тип элементов
 */
public final class Stack<T> {

    private final List<T> items = new ArrayList<>();

    /**
     * @throws NullPointerException если value равно null
     */
    public void push(T value) {
        Objects.requireNonNull(value, "value");
        items.add(value);
    }

    /**
     * Снимает и возвращает верхний элемент.
     *
     * @throws NoSuchElementException если стек пуст
     */
    public T pop() {
        requireNotEmpty();
        return items.remove(items.size() - 1);
    }

    /**
     * Возвращает верхний элемент, не снимая его.
     *
     * @throws NoSuchElementException если стек пуст
     */
    public T peek() {
        requireNotEmpty();
        return items.get(items.size() - 1);
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    private void requireNotEmpty() {
        if (items.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
    }
}

package edu.course.lab01;

import java.util.Arrays;

/**
 * Небольшие методы для первой лабораторной работы.
 */
public final class CourseToolkit {

    private CourseToolkit() {
        // Утилитарный класс не должен иметь экземпляров.
    }

    /**
     * Возвращает true, если число четное.
     */
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        int s = 0;
        if (Math.pow(Math.sqrt((double) number), 2) == number) {
            return false;
        }
        while (number > 0) {
            if (number % (number - 1) == 0) {
                s++;
            }
            number = number - 1;
        }
        return s <= 2;
    }

    public static boolean isPalindrome(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Not null");
        }
        StringBuilder strg = new StringBuilder(text);
        strg.reverse();
        return strg.toString().equals(text);
    }

    public static double average(int[] arr) {
        // Сначала проверяем на null, потом на длину!
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("not empty(nolь)");
        }
        int sum = Arrays.stream(arr).sum();
        return (double) sum / Arrays.stream(arr).count();
    }
}
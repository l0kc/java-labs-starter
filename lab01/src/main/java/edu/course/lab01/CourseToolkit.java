package edu.course.lab01;


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
        int res = 0;
        String texT = text;
        if (text == null) {
            throw new IllegalArgumentException("Not null");
        }
        for (int i = 0; i <= text.length() -1; i++){
            if (text.charAt(i) == texT.charAt(i)){
                res++;
            }
        }
        return res == text.length();
    }
    public static double average(int[] arr) {
        int sum = 0;
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("not empty(nolь)");
        }
        for(int i = 0; i <= arr.length - 1; i++){
            sum += arr[i];
        }
        return (double) sum / arr.length;
    }
}
package edu.course.lab01;


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
        if (Math.pow(Math.sqrt(number), 2) == number) {
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

    public static boolean isPalindrome(String text){
        if (text == null){
            throw new IllegalArgumentException("Строка не может быть null");
        }
        else {
            String lowerText = text.toLowerCase().replace(" ", "");
            String reversedText = new StringBuilder(lowerText).reverse().toString();
            return lowerText.equals(reversedText);
        }
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
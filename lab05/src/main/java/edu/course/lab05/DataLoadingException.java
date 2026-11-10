package edu.course.lab05;

/**
 * Ошибка источника данных: файл отсутствует, недоступен или не читается как UTF-8.
 * Исходная причина (cause) всегда сохраняется.
 * Ошибки отдельных записей сюда не попадают: они возвращаются в LoadResult.
 */
public final class DataLoadingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}

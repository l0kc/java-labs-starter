package edu.course.lab05;

import java.util.Objects;

/**
 * Ошибка одной записи: номер строки в файле (нумерация с 1), поле и сообщение.
 * Неизменяемый класс.
 */
public final class ValidationError {

    private final int lineNumber;
    private final String field;
    private final String message;

    public ValidationError(int lineNumber, String field, String message) {
        if (lineNumber < 1) {
            throw new IllegalArgumentException("lineNumber must be positive: " + lineNumber);
        }
        this.lineNumber = lineNumber;
        this.field = Objects.requireNonNull(field, "field");
        this.message = Objects.requireNonNull(message, "message");
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValidationError other = (ValidationError) o;
        return lineNumber == other.lineNumber
                && field.equals(other.field)
                && message.equals(other.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber, field, message);
    }

    @Override
    public String toString() {
        return "line " + lineNumber + ", field '" + field + "': " + message;
    }
}

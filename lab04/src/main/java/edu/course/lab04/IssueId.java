package edu.course.lab04;

import java.util.Objects;

/**
 * Идентификатор задачи: код проекта и номер. Неизменяемый класс, пригоден как ключ HashMap.
 * Логическое равенство определяют ОБА поля: projectCode и number.
 * Те же два поля используются в equals и в hashCode.
 */
public final class IssueId {

    private final String projectCode;
    private final int number;

    public IssueId(String projectCode, int number) {
        Objects.requireNonNull(projectCode, "projectCode");
        if (projectCode.isBlank()) {
            throw new IllegalArgumentException("Код проекта не может быть пустым");
        }
        if (number < 1) {
            throw new IllegalArgumentException("Номер задачи должен быть положительным: " + number);
        }
        this.projectCode = projectCode;
        this.number = number;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IssueId other = (IssueId) o;
        return number == other.number && projectCode.equals(other.projectCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectCode, number);
    }

    @Override
    public String toString() {
        return projectCode + "-" + number;
    }
}

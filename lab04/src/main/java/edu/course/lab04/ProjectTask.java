package edu.course.lab04;

import java.util.Objects;

/**
 * Минимальная версия задачи проекта: значение для IssueRegistry.
 */
public final class ProjectTask {

    private final String title;
    private final int effort;

    public ProjectTask(String title, int effort) {
        Objects.requireNonNull(title, "title");
        if (title.isBlank()) {
            throw new IllegalArgumentException("Название задачи не может быть пустым");
        }
        if (effort < 0) {
            throw new IllegalArgumentException("Трудоёмкость не может быть отрицательной: " + effort);
        }
        this.title = title;
        this.effort = effort;
    }

    public String getTitle() {
        return title;
    }

    public int getEffort() {
        return effort;
    }

    @Override
    public String toString() {
        return "ProjectTask{title=" + title + ", effort=" + effort + "}";
    }
}

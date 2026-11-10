package edu.course.lab03;

import java.util.Objects;

/**
 * Минимальная версия задачи проекта, нужная для стратегий приоритета.
 * Объект неизменяемый, поэтому стратегии не могут его испортить.
 */
public class ProjectTask {

    private final String title;
    private final int effort;
    private final TaskStatus status;

    /**
     * @param title  название задачи, не пустое
     * @param effort трудоёмкость (например, в часах), не отрицательная
     * @param status текущий статус
     */
    public ProjectTask(String title, int effort, TaskStatus status) {
        Objects.requireNonNull(title, "title");
        Objects.requireNonNull(status, "status");
        if (title.isBlank()) {
            throw new IllegalArgumentException("Название задачи не может быть пустым");
        }
        if (effort < 0) {
            throw new IllegalArgumentException("Трудоёмкость не может быть отрицательной: " + effort);
        }
        this.title = title;
        this.effort = effort;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public int getEffort() {
        return effort;
    }

    public TaskStatus getStatus() {
        return status;
    }
}

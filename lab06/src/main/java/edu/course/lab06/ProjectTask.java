package edu.course.lab06;

import java.util.Objects;

/**
 * Задача проекта. Неизменяемый класс, значения проверяются в конструкторе.
 */
public final class ProjectTask {

    private final String id;
    private final String title;
    private final String category;
    private final int estimatedHours;
    private final TaskStatus status;

    public ProjectTask(String id, String title, String category, int estimatedHours, TaskStatus status) {
        requireText(id, "id");
        requireText(title, "title");
        requireText(category, "category");
        if (estimatedHours <= 0) {
            throw new IllegalArgumentException("estimatedHours must be positive: " + estimatedHours);
        }
        Objects.requireNonNull(status, "status");
        this.id = id;
        this.title = title;
        this.category = category;
        this.estimatedHours = estimatedHours;
        this.status = status;
    }

    private static void requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ProjectTask{id=" + id + ", title=" + title + ", category=" + category
                + ", estimatedHours=" + estimatedHours + ", status=" + status + "}";
    }
}

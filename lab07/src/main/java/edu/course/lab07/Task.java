package edu.course.lab07;

import java.util.Objects;

/**
 * Minimal profile model for the lab: a task with a title, a category,
 * a status and an effort estimate (for example, story points).
 */
public record Task(String title, String category, TaskStatus status, int effort) {

    public Task {
        Objects.requireNonNull(title, "title");
        Objects.requireNonNull(category, "category");
        Objects.requireNonNull(status, "status");
    }
}

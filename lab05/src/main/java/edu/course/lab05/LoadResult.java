package edu.course.lab05;

import java.util.List;

/**
 * Результат загрузки: корректные объекты и ошибки отдельных записей.
 * Оба списка копируются и неизменяемы.
 */
public final class LoadResult {

    private final List<ProjectTask> tasks;
    private final List<ValidationError> errors;

    public LoadResult(List<ProjectTask> tasks, List<ValidationError> errors) {
        this.tasks = List.copyOf(tasks);
        this.errors = List.copyOf(errors);
    }

    public List<ProjectTask> getTasks() {
        return tasks;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}

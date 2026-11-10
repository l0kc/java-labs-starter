package edu.course.lab04;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Реестр задач. Построен на композиции: внутри хранится HashMap, наследования от него нет.
 * Null в роли ключа или значения не допускается.
 */
public class IssueRegistry {

    private final Map<IssueId, ProjectTask> tasks = new HashMap<>();

    /**
     * Добавляет задачу или заменяет существующую с равным ключом.
     *
     * @return прежнее значение или null, если ключа не было
     */
    public ProjectTask put(IssueId id, ProjectTask task) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(task, "task");
        return tasks.put(id, task);
    }

    /**
     * @return задача или null, если ключа нет
     */
    public ProjectTask get(IssueId id) {
        Objects.requireNonNull(id, "id");
        return tasks.get(id);
    }

    /**
     * @return удалённая задача или null, если ключа не было
     */
    public ProjectTask remove(IssueId id) {
        Objects.requireNonNull(id, "id");
        return tasks.remove(id);
    }

    public boolean containsKey(IssueId id) {
        Objects.requireNonNull(id, "id");
        return tasks.containsKey(id);
    }

    public int size() {
        return tasks.size();
    }
}

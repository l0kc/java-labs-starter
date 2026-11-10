package edu.course.lab03;

import java.util.Objects;

/**
 * Выбирает более приоритетную из двух задач. Конкретную политику не знает:
 * она приходит снаружи через конструктор.
 */
public class TaskPrioritizer {

    private final PriorityPolicy policy;

    public TaskPrioritizer(PriorityPolicy policy) {
        this.policy = Objects.requireNonNull(policy, "policy");
    }

    /**
     * @return задача с большим приоритетом; при равенстве приоритетов возвращается первая
     */
    public ProjectTask choose(ProjectTask first, ProjectTask second) {
        Objects.requireNonNull(first, "first");
        Objects.requireNonNull(second, "second");
        return policy.calculatePriority(second) > policy.calculatePriority(first) ? second : first;
    }
}

package edu.course.lab03;

import java.util.Objects;

/**
 * Приоритет определяется статусом: заблокированные задачи требуют внимания в первую очередь,
 * затем задачи в работе, затем ещё не начатые; завершённые задачи имеют наименьший приоритет.
 */
public class StatusPriorityPolicy implements PriorityPolicy {

    @Override
    public int calculatePriority(ProjectTask task) {
        Objects.requireNonNull(task, "task");
        switch (task.getStatus()) {
            case BLOCKED:
                return 3;
            case IN_PROGRESS:
                return 2;
            case TODO:
                return 1;
            case DONE:
                return 0;
            default:
                throw new IllegalStateException("Неизвестный статус: " + task.getStatus());
        }
    }
}

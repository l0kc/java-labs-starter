package edu.course.lab03;

import java.util.Objects;


public class EffortPriorityPolicy implements PriorityPolicy {

    @Override
    public int calculatePriority(ProjectTask task) {
        Objects.requireNonNull(task, "task");
        return task.getEffort();
    }
}

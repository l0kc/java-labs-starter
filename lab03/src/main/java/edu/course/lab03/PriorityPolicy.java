package edu.course.lab03;

/**
 * Контракт политики приоритета: чем больше число, тем выше приоритет задачи.
 */
public interface PriorityPolicy {

    int calculatePriority(ProjectTask task);
}

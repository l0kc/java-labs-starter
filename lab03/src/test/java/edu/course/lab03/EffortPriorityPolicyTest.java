package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EffortPriorityPolicyTest {

    private final PriorityPolicy policy = new EffortPriorityPolicy();

    @Test
    void priorityEqualsEffort() {
        ProjectTask task = new ProjectTask("Реализовать API", 8, TaskStatus.TODO);
        assertEquals(8, policy.calculatePriority(task));
    }

    @Test
    void greaterEffortGivesHigherPriority() {
        ProjectTask small = new ProjectTask("Правка", 1, TaskStatus.TODO);
        ProjectTask big = new ProjectTask("Модуль", 13, TaskStatus.TODO);
        assertTrue(policy.calculatePriority(big) > policy.calculatePriority(small));
    }

    @Test
    void nullTaskIsRejected() {
        assertThrows(NullPointerException.class, () -> policy.calculatePriority(null));
    }
}

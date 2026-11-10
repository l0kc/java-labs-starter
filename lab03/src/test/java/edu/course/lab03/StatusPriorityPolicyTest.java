package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StatusPriorityPolicyTest {

    private final PriorityPolicy policy = new StatusPriorityPolicy();

    private static ProjectTask taskWith(TaskStatus status) {
        return new ProjectTask("Задача", 5, status);
    }

    @Test
    void blockedHasHighestPriority() {
        assertEquals(3, policy.calculatePriority(taskWith(TaskStatus.BLOCKED)));
    }

    @Test
    void doneHasLowestPriority() {
        assertEquals(0, policy.calculatePriority(taskWith(TaskStatus.DONE)));
    }

    @Test
    void statusesAreOrdered() {
        int blocked = policy.calculatePriority(taskWith(TaskStatus.BLOCKED));
        int inProgress = policy.calculatePriority(taskWith(TaskStatus.IN_PROGRESS));
        int todo = policy.calculatePriority(taskWith(TaskStatus.TODO));
        int done = policy.calculatePriority(taskWith(TaskStatus.DONE));
        assertTrue(blocked > inProgress && inProgress > todo && todo > done);
    }

    @Test
    void nullTaskIsRejected() {
        assertThrows(NullPointerException.class, () -> policy.calculatePriority(null));
    }
}

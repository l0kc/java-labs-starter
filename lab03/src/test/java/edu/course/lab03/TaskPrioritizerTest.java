package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TaskPrioritizerTest {

    private final ProjectTask bigDone = new ProjectTask("Большая, но готова", 8, TaskStatus.DONE);
    private final ProjectTask smallBlocked = new ProjectTask("Маленькая, но заблокирована", 3, TaskStatus.BLOCKED);

    @Test
    void choosesTaskWithGreaterEffort() {
        TaskPrioritizer prioritizer = new TaskPrioritizer(new EffortPriorityPolicy());
        assertSame(bigDone, prioritizer.choose(bigDone, smallBlocked));
        assertSame(bigDone, prioritizer.choose(smallBlocked, bigDone));
    }

    @Test
    void choosesTaskWithHigherStatusPriority() {
        TaskPrioritizer prioritizer = new TaskPrioritizer(new StatusPriorityPolicy());
        assertSame(smallBlocked, prioritizer.choose(bigDone, smallBlocked));
        assertSame(smallBlocked, prioritizer.choose(smallBlocked, bigDone));
    }

    @Test
    void policyCanBeReplacedWithoutChangingPrioritizer() {
        // одни и те же две задачи, разные политики -> разный победитель
        PriorityPolicy[] policies = {new EffortPriorityPolicy(), new StatusPriorityPolicy()};
        ProjectTask[] expected = {bigDone, smallBlocked};

        for (int i = 0; i < policies.length; i++) {
            TaskPrioritizer prioritizer = new TaskPrioritizer(policies[i]);
            assertSame(expected[i], prioritizer.choose(bigDone, smallBlocked));
        }
    }

    @Test
    void tieReturnsFirstTask() {
        ProjectTask a = new ProjectTask("A", 5, TaskStatus.TODO);
        ProjectTask b = new ProjectTask("B", 5, TaskStatus.TODO);
        TaskPrioritizer prioritizer = new TaskPrioritizer(new EffortPriorityPolicy());
        assertSame(a, prioritizer.choose(a, b));
    }

    @Test
    void tasksAreNotModified() {
        TaskPrioritizer prioritizer = new TaskPrioritizer(new EffortPriorityPolicy());
        prioritizer.choose(bigDone, smallBlocked);
        assertEquals(8, bigDone.getEffort());
        assertEquals(TaskStatus.DONE, bigDone.getStatus());
        assertEquals(3, smallBlocked.getEffort());
        assertEquals(TaskStatus.BLOCKED, smallBlocked.getStatus());
    }

    @Test
    void nullPolicyIsRejected() {
        assertThrows(NullPointerException.class, () -> new TaskPrioritizer(null));
    }
}

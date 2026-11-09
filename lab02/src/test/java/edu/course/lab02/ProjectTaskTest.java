package edu.course.lab02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class ProjectTaskTest {

    @Test
    void createValidProjectTask() {
        ProjectTask task = new ProjectTask("TASK-101", "Реализовать REST API", TaskStatus.OPEN, 8);

        assertEquals("TASK-101", task.getId());
        assertEquals("Реализовать REST API", task.getTitle());
        assertEquals(TaskStatus.OPEN, task.getStatus());
        assertEquals(8, task.getEstHours());
        assertFalse(task.isCompleted());
    }

    @Test
    void errorInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask(null, "Task Title", TaskStatus.OPEN, 5));
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("   ", "Task Title", TaskStatus.OPEN, 5));
    }

    @Test
    void errorInvalidTitle() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("TASK-1", null, TaskStatus.OPEN, 5));
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("TASK-1", "", TaskStatus.OPEN, 5));
    }

    @Test
    void errorInvalidStatusOrHoursInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("TASK-1", "Title", null, 5));
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("TASK-1", "Title", TaskStatus.OPEN, 0));
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("TASK-1", "Title", TaskStatus.OPEN, -3));
    }

    @Test
    void changeStatusAndCheckCompletion() {
        ProjectTask task = new ProjectTask("TASK-1", "Title", TaskStatus.OPEN, 4);

        task.changeStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        assertFalse(task.isCompleted());

        task.changeStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getStatus());
        assertTrue(task.isCompleted());
    }

    @Test
    void ErrorWhenChangingStatusToNull() {
        ProjectTask task = new ProjectTask("TASK-1", "Title", TaskStatus.OPEN, 4);
        assertThrows(IllegalArgumentException.class, () -> task.changeStatus(null));
    }

    @Test
    void IncEstHours() {
        ProjectTask task = new ProjectTask("TASK-1", "Title", TaskStatus.OPEN, 10);
        task.IncEstHours(5);
        assertEquals(15, task.getEstHours());
    }

    @Test
    void ErrorNonPositiveExtraHours() {
        ProjectTask task = new ProjectTask("TASK-1", "Title", TaskStatus.OPEN, 10);
        assertThrows(IllegalArgumentException.class, () -> task.IncEstHours(0));
        assertThrows(IllegalArgumentException.class, () -> task.IncEstHours(-2));
    }
}
package edu.course.lab02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProjectTaskTest {

    @Test
    void changeStatus() {
        ProjectTask task = new ProjectTask("TASK-1", "Разработать дизайн", TaskStatus.OPEN, 10);

        assertEquals("TASK-1", task.getId());
        assertEquals("Разработать дизайн", task.getTitle());
        assertEquals(TaskStatus.OPEN, task.getStatus());
        assertEquals(10, task.getEstHours());
        assertFalse(task.isCompleted());
    }

    @Test
    void isCompleted() {
    }

    @Test
    void IncEstHours() {
    }

    @Test
    void getId() {
    }

    @Test
    void getTitle() {
    }

    @Test
    void getStatus() {
    }

    @Test
    void getEstHours() {
        ProjectTask task = new ProjectTask("TASK-8", "Трудоемкость", TaskStatus.OPEN, 5);

        assertThrows(IllegalArgumentException.class, () -> {
            task.IncEstHours(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            task.IncEstHours(-2);
        });

        assertEquals(5, task.getEstHours());
    }
    }
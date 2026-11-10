package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ProjectTaskTest {

    @Test
    void storesAllFields() {
        ProjectTask item = new ProjectTask("T-101", "Prepare release", "backend", 8, TaskStatus.READY);

        assertEquals("T-101", item.getId());
        assertEquals("Prepare release", item.getTitle());
        assertEquals("backend", item.getCategory());
        assertEquals(8, item.getEstimatedHours());
        assertEquals(TaskStatus.READY, item.getStatus());
    }

    @Test
    void invalidValuesAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProjectTask(" ", "Prepare release", "backend", 8, TaskStatus.READY));
        assertThrows(IllegalArgumentException.class,
                () -> new ProjectTask("T-101", "Prepare release", "backend", 0, TaskStatus.READY));
        assertThrows(NullPointerException.class,
                () -> new ProjectTask("T-101", "Prepare release", "backend", 8, null));
    }
}

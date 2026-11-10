package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class LoadResultTest {

    private static ProjectTask item() {
        return new ProjectTask("T-101", "Prepare release", "backend", 8, TaskStatus.READY);
    }

    @Test
    void resultDoesNotDependOnSourceLists() {
        List<ProjectTask> items = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();
        LoadResult result = new LoadResult(items, errors);

        items.add(item());
        errors.add(new ValidationError(2, "id", "must not be empty"));

        assertTrue(result.getTasks().isEmpty());
        assertFalse(result.hasErrors());
    }

    @Test
    void returnedListsAreUnmodifiable() {
        LoadResult result = new LoadResult(List.of(item()), List.of());

        assertThrows(UnsupportedOperationException.class, () -> result.getTasks().add(item()));
        assertThrows(UnsupportedOperationException.class,
                () -> result.getErrors().add(new ValidationError(2, "id", "m")));
        assertEquals(1, result.getTasks().size());
    }
}

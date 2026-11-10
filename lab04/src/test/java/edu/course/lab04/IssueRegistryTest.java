package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IssueRegistryTest {

    private final IssueRegistry registry = new IssueRegistry();

    @Test
    void newRegistryIsEmpty() {
        assertEquals(0, registry.size());
    }

    @Test
    void putAndGetReturnStoredTask() {
        ProjectTask task = new ProjectTask("Исправить баг", 3);
        registry.put(new IssueId("PROJ", 1), task);

        assertSame(task, registry.get(new IssueId("PROJ", 1)));
        assertEquals(1, registry.size());
    }

    @Test
    void twoEqualKeyInstancesFindSameValue() {
        IssueId stored = new IssueId("PROJ", 42);
        IssueId lookup = new IssueId("PROJ", 42);
        ProjectTask task = new ProjectTask("Задача", 5);

        registry.put(stored, task);

        assertNotSame(stored, lookup);
        assertSame(task, registry.get(lookup));
        assertTrue(registry.containsKey(lookup));
    }

    @Test
    void putWithEqualKeyReplacesValueAndKeepsSize() {
        ProjectTask oldTask = new ProjectTask("Старая", 1);
        ProjectTask newTask = new ProjectTask("Новая", 2);

        assertNull(registry.put(new IssueId("PROJ", 1), oldTask));
        ProjectTask previous = registry.put(new IssueId("PROJ", 1), newTask);

        assertSame(oldTask, previous);
        assertSame(newTask, registry.get(new IssueId("PROJ", 1)));
        assertEquals(1, registry.size());
    }

    @Test
    void getMissingKeyReturnsNull() {
        assertNull(registry.get(new IssueId("PROJ", 99)));
    }

    @Test
    void removeReturnsTaskAndDecreasesSize() {
        ProjectTask task = new ProjectTask("Задача", 2);
        registry.put(new IssueId("PROJ", 1), task);

        assertSame(task, registry.remove(new IssueId("PROJ", 1)));
        assertEquals(0, registry.size());
        assertFalse(registry.containsKey(new IssueId("PROJ", 1)));
    }

    @Test
    void removeMissingKeyReturnsNullAndKeepsSize() {
        registry.put(new IssueId("PROJ", 1), new ProjectTask("Задача", 2));

        assertNull(registry.remove(new IssueId("PROJ", 2)));
        assertEquals(1, registry.size());
    }

    @Test
    void containsKeyDistinguishesPresentAndAbsentKeys() {
        registry.put(new IssueId("PROJ", 1), new ProjectTask("Задача", 2));

        assertTrue(registry.containsKey(new IssueId("PROJ", 1)));
        assertFalse(registry.containsKey(new IssueId("PROJ", 2)));
        assertFalse(registry.containsKey(new IssueId("OTHER", 1)));
    }

    @Test
    void unequalKeysWithSameHashCodeStoreDifferentValues() {
        // "Aa" и "BB" имеют одинаковый String.hashCode(), поэтому ключи сталкиваются
        IssueId first = new IssueId("Aa", 7);
        IssueId second = new IssueId("BB", 7);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, second);

        ProjectTask firstTask = new ProjectTask("Первая", 1);
        ProjectTask secondTask = new ProjectTask("Вторая", 2);
        registry.put(first, firstTask);
        registry.put(second, secondTask);

        assertEquals(2, registry.size());
        assertSame(firstTask, registry.get(new IssueId("Aa", 7)));
        assertSame(secondTask, registry.get(new IssueId("BB", 7)));
    }

    @Test
    void nullKeyAndNullValueAreRejected() {
        assertThrows(NullPointerException.class, () -> registry.put(null, new ProjectTask("Задача", 1)));
        assertThrows(NullPointerException.class, () -> registry.put(new IssueId("PROJ", 1), null));
        assertThrows(NullPointerException.class, () -> registry.get(null));
        assertThrows(NullPointerException.class, () -> registry.remove(null));
        assertThrows(NullPointerException.class, () -> registry.containsKey(null));
    }
}

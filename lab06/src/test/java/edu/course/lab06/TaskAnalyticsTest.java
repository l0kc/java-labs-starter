package edu.course.lab06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TaskAnalyticsTest {

    private static ProjectTask item(String id, String group, int estimatedHours, TaskStatus status) {
        return new ProjectTask(id, "Prepare release", group, estimatedHours, status);
    }

    /** Шесть элементов: статусы NEW x2, READY x3, DONE x1; значения estimatedHours 5, 3, 8, 2, 5, 1. */
    private static List<ProjectTask> fixture() {
        List<ProjectTask> items = new ArrayList<>();
        items.add(item("T-101", "backend", 5, TaskStatus.NEW));
        items.add(item("T-102", "frontend", 3, TaskStatus.READY));
        items.add(item("T-103", "backend", 8, TaskStatus.READY));
        items.add(item("T-104", "qa", 2, TaskStatus.NEW));
        items.add(item("T-105", "frontend", 5, TaskStatus.READY));
        items.add(item("T-106", "backend", 1, TaskStatus.DONE));
        return items;
    }

    private static int countOf(Map<TaskStatus, Integer> counts, TaskStatus status) {
        return counts.get(status);
    }

    private static List<String> ids(List<ProjectTask> items) {
        List<String> result = new ArrayList<>();
        for (ProjectTask item : items) {
            result.add(item.getId());
        }
        return result;
    }

    // ---------- подсчёт по статусам (Map) ----------

    @Test
    void countsItemsByStatus() {
        Map<TaskStatus, Integer> counts = TaskAnalytics.countByStatus(fixture());

        assertEquals(2, countOf(counts, TaskStatus.NEW));
        assertEquals(3, countOf(counts, TaskStatus.READY));
        assertEquals(1, countOf(counts, TaskStatus.DONE));
        assertEquals(3, counts.size());
    }

    @Test
    void statusWithoutItemsHasNoEntry() {
        List<ProjectTask> items = new ArrayList<>();
        items.add(item("T-101", "backend", 5, TaskStatus.NEW));

        Map<TaskStatus, Integer> counts = TaskAnalytics.countByStatus(items);

        assertEquals(1, counts.size());
        assertFalse(counts.containsKey(TaskStatus.READY));
    }

    @Test
    void countByStatusKeepsEnumDeclarationOrder() {
        Map<TaskStatus, Integer> counts = TaskAnalytics.countByStatus(fixture());

        assertEquals(List.of(TaskStatus.NEW, TaskStatus.READY, TaskStatus.DONE),
                new ArrayList<>(counts.keySet()));
    }

    @Test
    void countByStatusOfEmptyListIsEmpty() {
        assertTrue(TaskAnalytics.countByStatus(new ArrayList<>()).isEmpty());
    }

    // ---------- уникальные значения (Set) ----------

    @Test
    void duplicatesAreCollapsed() {
        Set<String> unique = TaskAnalytics.uniqueCategories(fixture());

        assertEquals(3, unique.size());
        assertTrue(unique.contains("backend"));
        assertTrue(unique.contains("frontend"));
        assertTrue(unique.contains("qa"));
    }

    @Test
    void uniqueValuesAreInAlphabeticalOrderRegardlessOfInputOrder() {
        List<ProjectTask> items = fixture();
        Collections.reverse(items);

        Set<String> unique = TaskAnalytics.uniqueCategories(items);

        assertEquals(List.of("backend", "frontend", "qa"), new ArrayList<>(unique));
    }

    @Test
    void uniqueValuesOfEmptyListIsEmpty() {
        assertTrue(TaskAnalytics.uniqueCategories(new ArrayList<>()).isEmpty());
    }

    // ---------- сортировка (List + Comparator) ----------

    @Test
    void sortsByValueThenById() {
        List<ProjectTask> sorted = TaskAnalytics.sortedByEffortAndId(fixture());

        assertEquals(List.of("T-106", "T-104", "T-102", "T-101", "T-105", "T-103"), ids(sorted));
    }

    @Test
    void equalValuesAreOrderedById() {
        List<ProjectTask> items = new ArrayList<>();
        items.add(item("T-105", "backend", 5, TaskStatus.NEW));
        items.add(item("T-101", "backend", 5, TaskStatus.NEW));
        items.add(item("T-103", "backend", 5, TaskStatus.NEW));

        assertEquals(List.of("T-101", "T-103", "T-105"), ids(TaskAnalytics.sortedByEffortAndId(items)));
    }

    @Test
    void sortedListIsNewAndSourceIsNotModified() {
        List<ProjectTask> source = fixture();
        List<ProjectTask> snapshot = new ArrayList<>(source);

        List<ProjectTask> sorted = TaskAnalytics.sortedByEffortAndId(source);

        assertNotSame(source, sorted);
        assertEquals(snapshot, source);                       // порядок и состав не изменились
        sorted.clear();
        assertEquals(snapshot.size(), source.size());         // результат независим от исходного списка
    }

    @Test
    void sortingEmptyListGivesEmptyList() {
        assertTrue(TaskAnalytics.sortedByEffortAndId(new ArrayList<>()).isEmpty());
    }

    // ---------- общие свойства ----------

    @Test
    void noOperationModifiesSourceList() {
        List<ProjectTask> source = fixture();
        List<ProjectTask> snapshot = new ArrayList<>(source);

        TaskAnalytics.countByStatus(source);
        TaskAnalytics.uniqueCategories(source);
        TaskAnalytics.sortedByEffortAndId(source);

        assertEquals(snapshot, source);
    }

    @Test
    void nullListIsRejected() {
        assertThrows(NullPointerException.class, () -> TaskAnalytics.countByStatus(null));
        assertThrows(NullPointerException.class, () -> TaskAnalytics.uniqueCategories(null));
        assertThrows(NullPointerException.class, () -> TaskAnalytics.sortedByEffortAndId(null));
    }

    @Test
    void nullElementIsRejected() {
        List<ProjectTask> items = new ArrayList<>();
        items.add(null);

        assertThrows(NullPointerException.class, () -> TaskAnalytics.countByStatus(items));
        assertThrows(NullPointerException.class, () -> TaskAnalytics.uniqueCategories(items));
        assertThrows(NullPointerException.class, () -> TaskAnalytics.sortedByEffortAndId(items));
    }
}

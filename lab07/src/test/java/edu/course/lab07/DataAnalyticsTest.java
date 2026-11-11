package edu.course.lab07;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DataAnalyticsTest {

    private static final Task LOGIN_FORM = new Task("Login form", "Frontend", TaskStatus.DONE, 3);
    private static final Task REST_API = new Task("REST API", "backend", TaskStatus.IN_PROGRESS, 8);
    private static final Task DB_SCHEMA = new Task("Database schema", "  Backend ", TaskStatus.DONE, 5);
    private static final Task CI_PIPELINE = new Task("CI pipeline", "DevOps", TaskStatus.TODO, 5);
    private static final Task WRITE_DOCS = new Task("Write docs", "docs", TaskStatus.TODO, 2);

    /** Mutable list that is deliberately not sorted by any key used in the tests. */
    private static List<Task> sampleTasks() {
        return new ArrayList<>(List.of(LOGIN_FORM, REST_API, DB_SCHEMA, CI_PIPELINE, WRITE_DOCS));
    }

    // ---- 1. filterByStatus ----

    @Test
    void filterByStatusOfEmptyListIsEmpty() {
        assertEquals(List.of(), DataAnalytics.filterByStatus(List.of(), TaskStatus.DONE));
    }

    @Test
    void filterByStatusOfSingleMatchingTaskKeepsIt() {
        assertEquals(List.of(WRITE_DOCS), DataAnalytics.filterByStatus(List.of(WRITE_DOCS), TaskStatus.TODO));
    }

    @Test
    void filterByStatusReturnsEmptyListWhenNothingMatches() {
        assertEquals(List.of(), DataAnalytics.filterByStatus(List.of(WRITE_DOCS), TaskStatus.DONE));
    }

    @Test
    void filterByStatusKeepsOriginalOrderOfMatchingTasks() {
        assertEquals(List.of(LOGIN_FORM, DB_SCHEMA),
                DataAnalytics.filterByStatus(sampleTasks(), TaskStatus.DONE));
        assertEquals(List.of(CI_PIPELINE, WRITE_DOCS),
                DataAnalytics.filterByStatus(sampleTasks(), TaskStatus.TODO));
        assertEquals(List.of(REST_API),
                DataAnalytics.filterByStatus(sampleTasks(), TaskStatus.IN_PROGRESS));
    }

    // ---- 2. sortedNormalizedCategories ----

    @Test
    void sortedNormalizedCategoriesOfEmptyListIsEmpty() {
        assertEquals(List.of(), DataAnalytics.sortedNormalizedCategories(List.of()));
    }

    @Test
    void sortedNormalizedCategoriesOfSingleTask() {
        assertEquals(List.of("frontend"), DataAnalytics.sortedNormalizedCategories(List.of(LOGIN_FORM)));
    }

    @Test
    void sortedNormalizedCategoriesTrimsLowerCasesDeduplicatesAndSorts() {
        assertEquals(List.of("backend", "devops", "docs", "frontend"),
                DataAnalytics.sortedNormalizedCategories(sampleTasks()));
    }

    // ---- 3. countByCategory ----

    @Test
    void countByCategoryOfEmptyListIsEmptyMap() {
        assertEquals(Map.of(), DataAnalytics.countByCategory(List.of()));
    }

    @Test
    void countByCategoryOfSingleTaskHasOneGroup() {
        assertEquals(Map.of("docs", 1L), DataAnalytics.countByCategory(List.of(WRITE_DOCS)));
    }

    @Test
    void countByCategoryGroupsByNormalizedCategoryInAlphabeticalOrder() {
        Map<String, Long> counts = DataAnalytics.countByCategory(sampleTasks());

        assertEquals(Map.of("backend", 2L, "devops", 1L, "docs", 1L, "frontend", 1L), counts);
        assertEquals(List.of("backend", "devops", "docs", "frontend"), List.copyOf(counts.keySet()));
    }

    // ---- 4. findMaxEffort ----

    @Test
    void findMaxEffortOfEmptyListIsEmptyOptional() {
        assertEquals(Optional.empty(), DataAnalytics.findMaxEffort(List.of()));
    }

    @Test
    void findMaxEffortOfSingleTaskReturnsThatTask() {
        assertEquals(Optional.of(WRITE_DOCS), DataAnalytics.findMaxEffort(List.of(WRITE_DOCS)));
    }

    @Test
    void findMaxEffortReturnsTaskWithBiggestEffort() {
        assertEquals(Optional.of(REST_API), DataAnalytics.findMaxEffort(sampleTasks()));
    }

    @Test
    void findMaxEffortPrefersAlphabeticallyFirstTitleOnEqualEffort() {
        Task zeta = new Task("Zeta", "misc", TaskStatus.TODO, 9);
        Task alpha = new Task("Alpha", "misc", TaskStatus.TODO, 9);

        assertEquals(Optional.of(alpha), DataAnalytics.findMaxEffort(List.of(zeta, alpha)));
        assertEquals(Optional.of(alpha), DataAnalytics.findMaxEffort(List.of(alpha, zeta)));
    }

    // ---- 5. sortedByEffortDescThenTitle ----

    @Test
    void sortedByEffortOfEmptyListIsEmpty() {
        assertEquals(List.of(), DataAnalytics.sortedByEffortDescThenTitle(List.of()));
    }

    @Test
    void sortedByEffortOfSingleTaskKeepsIt() {
        assertEquals(List.of(WRITE_DOCS), DataAnalytics.sortedByEffortDescThenTitle(List.of(WRITE_DOCS)));
    }

    @Test
    void sortedByEffortDescendingThenByTitle() {
        assertEquals(List.of(REST_API, CI_PIPELINE, DB_SCHEMA, LOGIN_FORM, WRITE_DOCS),
                DataAnalytics.sortedByEffortDescThenTitle(sampleTasks()));
    }

    @Test
    void sortedByEffortUsesTitleWhenEffortIsEqual() {
        Task beta = new Task("Beta", "misc", TaskStatus.TODO, 4);
        Task alpha = new Task("Alpha", "misc", TaskStatus.TODO, 4);

        assertEquals(List.of(alpha, beta), DataAnalytics.sortedByEffortDescThenTitle(List.of(beta, alpha)));
    }

    @Test
    void sortedByEffortKeepsInputOrderWhenBothKeysAreEqual() {
        Task first = new Task("Same", "a", TaskStatus.TODO, 1);
        Task second = new Task("Same", "b", TaskStatus.DONE, 1);

        assertEquals(List.of(first, second), DataAnalytics.sortedByEffortDescThenTitle(List.of(first, second)));
        assertEquals(List.of(second, first), DataAnalytics.sortedByEffortDescThenTitle(List.of(second, first)));
    }

    // ---- extra part: averageEffortByCategory ----

    @Test
    void averageEffortOfEmptyListIsEmptyMap() {
        assertEquals(Map.of(), DataAnalytics.averageEffortByCategory(List.of()));
    }

    @Test
    void averageEffortIsComputedPerNormalizedCategory() {
        assertEquals(Map.of("backend", 6.5, "devops", 5.0, "docs", 2.0, "frontend", 3.0),
                DataAnalytics.averageEffortByCategory(sampleTasks()));
    }

    // ---- the input list is never modified ----

    @Test
    void analyticsMethodsDoNotModifyInputList() {
        List<Task> input = sampleTasks();
        List<Task> copyBefore = new ArrayList<>(input);

        DataAnalytics.filterByStatus(input, TaskStatus.DONE);
        DataAnalytics.sortedNormalizedCategories(input);
        DataAnalytics.countByCategory(input);
        DataAnalytics.findMaxEffort(input);
        DataAnalytics.sortedByEffortDescThenTitle(input);
        DataAnalytics.averageEffortByCategory(input);

        assertEquals(copyBefore, input);
    }
}

package edu.course.lab07;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Stream-based analytics over a list of {@link Task}s.
 *
 * <p>All methods are pure: they never modify the input list, never print
 * anything and build the result with a terminal stream operation only.
 * Returned lists are unmodifiable.
 */
public final class DataAnalytics {

    /** Biggest effort first; tasks with equal effort are ordered by title (A-Z). */
    private static final Comparator<Task> BY_EFFORT_DESC_THEN_TITLE =
            Comparator.comparingInt(Task::effort).reversed()
                    .thenComparing(Task::title);

    private DataAnalytics() {
    }

    /** 1. Tasks with the given status, in the original order. */
    public static List<Task> filterByStatus(List<Task> tasks, TaskStatus status) {
        return tasks.stream()
                .filter(task -> task.status() == status)
                .toList();
    }

    /** 2. Unique categories, trimmed and lower-cased, sorted alphabetically. */
    public static List<String> sortedNormalizedCategories(List<Task> tasks) {
        return tasks.stream()
                .map(DataAnalytics::normalizedCategory)
                .distinct()
                .sorted()
                .toList();
    }

    /** 3. Number of tasks per normalized category; keys are in alphabetical order. */
    public static Map<String, Long> countByCategory(List<Task> tasks) {
        return tasks.stream()
                .collect(Collectors.groupingBy(
                        DataAnalytics::normalizedCategory,
                        TreeMap::new,
                        Collectors.counting()));
    }

    /**
     * 4. The task with the biggest effort, or an empty Optional for an empty list.
     * On equal effort the task whose title comes first alphabetically wins,
     * so the result does not depend on the order of the input.
     */
    public static Optional<Task> findMaxEffort(List<Task> tasks) {
        return tasks.stream()
                .max(Comparator.comparingInt(Task::effort)
                        .thenComparing(Task::title, Comparator.reverseOrder()));
    }

    /** 5. New list sorted by effort (descending), then by title (ascending). */
    public static List<Task> sortedByEffortDescThenTitle(List<Task> tasks) {
        return tasks.stream()
                .sorted(BY_EFFORT_DESC_THEN_TITLE)
                .toList();
    }

    /** Extra part: average effort per normalized category; keys are in alphabetical order. */
    public static Map<String, Double> averageEffortByCategory(List<Task> tasks) {
        return tasks.stream()
                .collect(Collectors.groupingBy(
                        DataAnalytics::normalizedCategory,
                        TreeMap::new,
                        Collectors.averagingInt(Task::effort)));
    }

    private static String normalizedCategory(Task task) {
        return task.category().strip().toLowerCase(Locale.ROOT);
    }
}

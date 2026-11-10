package edu.course.lab06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Аналитика над списком задач обычными коллекциями и циклами.
 * Исходный список ни одним методом не изменяется. Null-список и null-элементы запрещены.
 */
public final class TaskAnalytics {

    private static final Comparator<ProjectTask> ORDER = new OrderComparator();

    private TaskAnalytics() {
    }

    /**
     * Число элементов по статусам. Реализация Map — EnumMap: ключи являются константами enum.
     * Статус, которого нет в данных, записи не получает. Порядок обхода — порядок констант enum.
     */
    public static Map<TaskStatus, Integer> countByStatus(List<ProjectTask> items) {
        Objects.requireNonNull(items, "items");
        Map<TaskStatus, Integer> counts = new EnumMap<>(TaskStatus.class);
        for (ProjectTask item : items) {
            TaskStatus status = Objects.requireNonNull(item, "item").getStatus();
            counts.put(status, counts.getOrDefault(status, 0) + 1);
        }
        return counts;
    }

    /**
     * Уникальные значения поля {@code category}. Реализация Set — TreeSet:
     * повторов нет, порядок алфавитный и не зависит от порядка входных данных.
     */
    public static Set<String> uniqueCategories(List<ProjectTask> items) {
        Objects.requireNonNull(items, "items");
        Set<String> result = new TreeSet<>();
        for (ProjectTask item : items) {
            result.add(Objects.requireNonNull(item, "item").getCategory());
        }
        return result;
    }

    /**
     * Новый список, отсортированный по трудоёмкости и идентификатору (по возрастанию).
     * Исходный список не изменяется.
     */
    public static List<ProjectTask> sortedByEffortAndId(List<ProjectTask> items) {
        Objects.requireNonNull(items, "items");
        List<ProjectTask> copy = new ArrayList<>();
        for (ProjectTask item : items) {
            copy.add(Objects.requireNonNull(item, "item"));
        }
        copy.sort(ORDER);
        return copy;
    }

    /**
     * Сначала по estimatedHours, при равенстве — по id. Сравнение через Integer.compare и
     * String.compareTo: вычитание чисел может переполниться и дать неверный знак.
     */
    private static final class OrderComparator implements Comparator<ProjectTask> {

        @Override
        public int compare(ProjectTask first, ProjectTask second) {
            int byValue = Integer.compare(first.getEstimatedHours(), second.getEstimatedHours());
            if (byValue != 0) {
                return byValue;
            }
            return first.getId().compareTo(second.getId());
        }
    }
}

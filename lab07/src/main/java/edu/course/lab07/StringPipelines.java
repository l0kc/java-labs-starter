package edu.course.lab07;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

/** Preparatory part of the lab: a simple filter-map-sort pipeline over strings. */
public final class StringPipelines {

    private StringPipelines() {
    }

    /**
     * Returns a new list with the non-empty strings of {@code source} in upper case,
     * sorted by length first and alphabetically second. The source list is not modified.
     *
     * <p>Example: {@code ["java", "", "git", "maven", "api"]} gives
     * {@code ["API", "GIT", "JAVA", "MAVEN"]}.
     */
    public static List<String> nonEmptyUpperCaseSorted(List<String> source) {
        return source.stream()
                .filter(Predicate.not(String::isEmpty))
                .map(s -> s.toUpperCase(Locale.ROOT))
                .sorted(Comparator.comparingInt(String::length)
                        .thenComparing(Comparator.naturalOrder()))
                .toList();
    }
}

package edu.course.lab07;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class StringPipelinesTest {

    @Test
    void keepsNonEmptyStringsUpperCasesThemAndSortsByLengthThenAlphabet() {
        List<String> source = new ArrayList<>(List.of("java", "", "git", "maven", "api"));
        List<String> copyBefore = new ArrayList<>(source);

        List<String> result = StringPipelines.nonEmptyUpperCaseSorted(source);

        assertEquals(List.of("API", "GIT", "JAVA", "MAVEN"), result);
        assertEquals(copyBefore, source);
    }

    @Test
    void emptyListGivesEmptyResult() {
        assertEquals(List.of(), StringPipelines.nonEmptyUpperCaseSorted(new ArrayList<>()));
    }

    @Test
    void listOfOnlyEmptyStringsGivesEmptyResult() {
        assertEquals(List.of(), StringPipelines.nonEmptyUpperCaseSorted(List.of("", "")));
    }
}

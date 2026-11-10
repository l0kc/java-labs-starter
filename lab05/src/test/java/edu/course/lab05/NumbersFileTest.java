package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Подготовительная часть: временный UTF-8 файл с тремя строками "10", "bad", "20".
 * Файл создаётся в каталоге @TempDir и в Git не попадает.
 */
class NumbersFileTest {

    @TempDir
    Path tempDir;

    private Path createNumbersFile() throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);
        return file;
    }

    private static List<Integer> parseAll(List<String> lines) {
        List<Integer> numbers = new ArrayList<>();
        for (String line : lines) {
            numbers.add(Integer.parseInt(line));
        }
        return numbers;
    }

    @Test
    void fileContentIsReadAsThreeLines() throws IOException {
        List<String> lines = Files.readAllLines(createNumbersFile(), StandardCharsets.UTF_8);

        assertEquals(List.of("10", "bad", "20"), lines);
    }

    @Test
    void validLinesAreConvertedToIntegers() throws IOException {
        List<String> lines = Files.readAllLines(createNumbersFile(), StandardCharsets.UTF_8);

        assertEquals(10, Integer.parseInt(lines.get(0)));
        assertEquals(20, Integer.parseInt(lines.get(2)));
    }

    @Test
    void badLineCausesNumberFormatException() throws IOException {
        List<String> lines = Files.readAllLines(createNumbersFile(), StandardCharsets.UTF_8);

        assertThrows(NumberFormatException.class, () -> parseAll(lines));
    }
}

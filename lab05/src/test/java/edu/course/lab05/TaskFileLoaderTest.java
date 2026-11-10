package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TaskFileLoaderTest {

    private static final String HEADER = "id;title;category;estimatedHours;status";
    private static final String VALID_ROW = "T-101;Prepare release;backend;8;READY";

    @TempDir
    Path tempDir;

    private final TaskFileLoader loader = new TaskFileLoader();

    private Path writeLines(String... lines) throws IOException {
        Path file = tempDir.resolve("data.csv");
        Files.write(file, Arrays.asList(lines), StandardCharsets.UTF_8);
        return file;
    }

    private static void assertSingleError(LoadResult result, int line, String field) {
        assertTrue(result.getTasks().isEmpty());
        assertEquals(1, result.getErrors().size());
        assertEquals(line, result.getErrors().get(0).getLineNumber());
        assertEquals(field, result.getErrors().get(0).getField());
    }

    // ---------- корректные данные ----------

    @Test
    void loadsValidFile() throws IOException {
        Path file = writeLines(HEADER, VALID_ROW, "T-102;Write tests;qa;3;DONE");

        LoadResult result = loader.load(file);

        assertFalse(result.hasErrors());
        assertEquals(2, result.getTasks().size());
        ProjectTask first = result.getTasks().get(0);
        assertEquals("T-101", first.getId());
        assertEquals("Prepare release", first.getTitle());
        assertEquals("backend", first.getCategory());
        assertEquals(8, first.getEstimatedHours());
        assertEquals(TaskStatus.READY, first.getStatus());
        assertEquals(TaskStatus.DONE, result.getTasks().get(1).getStatus());
    }

    @Test
    void loadsCyrillicTextAsUtf8() throws IOException {
        Path file = writeLines(HEADER, "T-101;Подготовить релиз;бэкенд;8;READY");

        LoadResult result = loader.load(file);

        assertFalse(result.hasErrors());
        assertEquals("Подготовить релиз", result.getTasks().get(0).getTitle());
        assertEquals("бэкенд", result.getTasks().get(0).getCategory());
    }

    @Test
    void trimsWhitespaceAroundFields() throws IOException {
        Path file = writeLines(HEADER, "  T-101 ; Prepare release ;  backend; 8 ; READY ");

        LoadResult result = loader.load(file);

        assertFalse(result.hasErrors());
        assertEquals("T-101", result.getTasks().get(0).getId());
        assertEquals("Prepare release", result.getTasks().get(0).getTitle());
        assertEquals(8, result.getTasks().get(0).getEstimatedHours());
    }

    @Test
    void acceptsUtf8ByteOrderMarkBeforeHeader() throws IOException {
        Path file = tempDir.resolve("bom.csv");
        String content = "\uFEFF" + HEADER + "\n" + VALID_ROW + "\n";
        Files.write(file, content.getBytes(StandardCharsets.UTF_8));

        LoadResult result = loader.load(file);

        assertFalse(result.hasErrors());
        assertEquals(1, result.getTasks().size());
    }

    @Test
    void headerOnlyFileGivesEmptyResult() throws IOException {
        LoadResult result = loader.load(writeLines(HEADER));

        assertTrue(result.getTasks().isEmpty());
        assertFalse(result.hasErrors());
    }

    @Test
    void blankLinesAreSkippedButStillCounted() throws IOException {
        Path file = writeLines(HEADER, "", VALID_ROW, "   ", "T-102;Write tests;qa;abc;READY");

        LoadResult result = loader.load(file);

        assertEquals(1, result.getTasks().size());
        assertEquals(1, result.getErrors().size());
        assertEquals(5, result.getErrors().get(0).getLineNumber());
    }

    // ---------- ошибки записей ----------

    @Test
    void reportsEmptyTextField() throws IOException {
        LoadResult result = loader.load(writeLines(HEADER, "T-101;;backend;8;READY"));

        assertSingleError(result, 2, "title");
    }

    @Test
    void reportsEmptyNumberField() throws IOException {
        LoadResult result = loader.load(writeLines(HEADER, "T-101;Prepare release;backend;;READY"));

        assertSingleError(result, 2, "estimatedHours");
    }

    @Test
    void reportsEmptyLastFieldAfterTrailingSeparator() throws IOException {
        LoadResult result = loader.load(writeLines(HEADER, "T-101;Prepare release;backend;8;"));

        assertSingleError(result, 2, "status");
    }

    @Test
    void reportsInvalidNumber() throws IOException {
        LoadResult result = loader.load(writeLines(HEADER, "T-101;Prepare release;backend;abc;READY"));

        assertSingleError(result, 2, "estimatedHours");
    }

    @Test
    void reportsNonPositiveNumbers() throws IOException {
        LoadResult result = loader.load(writeLines(HEADER,
                "T-101;Prepare release;backend;0;READY",
                "T-102;Write tests;qa;-5;READY"));

        assertTrue(result.getTasks().isEmpty());
        assertEquals(2, result.getErrors().size());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals(3, result.getErrors().get(1).getLineNumber());
        assertEquals("estimatedHours", result.getErrors().get(0).getField());
        assertEquals("estimatedHours", result.getErrors().get(1).getField());
    }

    @Test
    void reportsUnknownStatus() throws IOException {
        LoadResult result = loader.load(writeLines(HEADER,
                "T-101;Prepare release;backend;8;ARCHIVED",
                "T-102;Write tests;qa;3;ready"));

        assertTrue(result.getTasks().isEmpty());
        assertEquals(2, result.getErrors().size());
        assertEquals("status", result.getErrors().get(0).getField());
        assertEquals("status", result.getErrors().get(1).getField());
    }

    @Test
    void reportsTooFewColumns() throws IOException {
        LoadResult result = loader.load(writeLines(HEADER, "T-101;Prepare release;backend;8"));

        assertSingleError(result, 2, "columns");
    }

    @Test
    void reportsTooManyColumns() throws IOException {
        LoadResult result = loader.load(writeLines(HEADER, "T-101;Prepare release;backend;8;READY;extra"));

        assertSingleError(result, 2, "columns");
    }

    @Test
    void reportsEveryInvalidFieldOfOneRow() throws IOException {
        LoadResult result = loader.load(writeLines(HEADER, "T-101;;backend;abc;ARCHIVED"));

        assertTrue(result.getTasks().isEmpty());
        assertEquals(3, result.getErrors().size());
        assertEquals("title", result.getErrors().get(0).getField());
        assertEquals("estimatedHours", result.getErrors().get(1).getField());
        assertEquals("status", result.getErrors().get(2).getField());
    }

    @Test
    void validAndInvalidRowsAreProcessedTogether() throws IOException {
        Path file = writeLines(HEADER,
                "T-101;Prepare release;backend;8;READY",         // 2: корректная
                "T-102;Write tests;qa;abc;READY",       // 3: неверное число
                "T-103;Prepare release;qa;5;READY",         // 4: корректная
                "T-104;Write tests;backend;2;ARCHIVED",      // 5: неизвестный статус
                "T-105;Prepare release",                         // 6: неверное число столбцов
                "T-106;Write tests;qa;1;DONE"); // 7: корректная

        LoadResult result = loader.load(file);

        assertEquals(3, result.getTasks().size());
        assertEquals("T-101", result.getTasks().get(0).getId());
        assertEquals("T-103", result.getTasks().get(1).getId());
        assertEquals("T-106", result.getTasks().get(2).getId());

        List<ValidationError> errors = result.getErrors();
        assertEquals(3, errors.size());
        assertEquals(3, errors.get(0).getLineNumber());
        assertEquals("estimatedHours", errors.get(0).getField());
        assertEquals(5, errors.get(1).getLineNumber());
        assertEquals("status", errors.get(1).getField());
        assertEquals(6, errors.get(2).getLineNumber());
        assertEquals("columns", errors.get(2).getField());
    }

    // ---------- заголовок ----------

    @Test
    void wrongHeaderIsReportedAndRowsAreNotProcessed() throws IOException {
        Path file = writeLines("id;name;category;estimatedHours;status", VALID_ROW);

        LoadResult result = loader.load(file);

        assertSingleError(result, 1, "header");
    }

    @Test
    void emptyFileReportsMissingHeader() throws IOException {
        Path file = tempDir.resolve("empty.csv");
        Files.write(file, new byte[0]);

        LoadResult result = loader.load(file);

        assertSingleError(result, 1, "header");
    }

    // ---------- ошибки источника ----------

    @Test
    void missingFileThrowsDataLoadingExceptionWithCause() {
        Path missing = tempDir.resolve("missing.csv");

        DataLoadingException exception =
                assertThrows(DataLoadingException.class, () -> loader.load(missing));

        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof IOException);
        assertTrue(exception.getMessage().contains("missing.csv"));
    }

    @Test
    void directoryInsteadOfFileThrowsDataLoadingException() {
        DataLoadingException exception =
                assertThrows(DataLoadingException.class, () -> loader.load(tempDir));

        assertTrue(exception.getCause() instanceof IOException);
    }

    @Test
    void invalidUtf8ThrowsDataLoadingExceptionWithCause() throws IOException {
        Path file = tempDir.resolve("broken.csv");
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        content.writeBytes((HEADER + "\n").getBytes(StandardCharsets.UTF_8));
        content.writeBytes(new byte[] {(byte) 0xC3, (byte) 0x28, '\n'}); // недопустимая последовательность UTF-8
        Files.write(file, content.toByteArray());

        DataLoadingException exception =
                assertThrows(DataLoadingException.class, () -> loader.load(file));

        assertTrue(exception.getCause() instanceof IOException);
    }

    @Test
    void nullPathIsRejected() {
        assertThrows(NullPointerException.class, () -> loader.load(null));
    }

    // ---------- контракт «ничего не печатать» ----------

    @Test
    void loaderPrintsNothing() throws IOException {
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(err, true, StandardCharsets.UTF_8));
        try {
            loader.load(writeLines(HEADER, VALID_ROW, "broken row"));
            assertThrows(DataLoadingException.class, () -> loader.load(tempDir.resolve("missing.csv")));
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }

        assertEquals(0, out.size());
        assertEquals(0, err.size());
    }
}

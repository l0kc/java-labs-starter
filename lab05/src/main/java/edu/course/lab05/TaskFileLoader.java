package edu.course.lab05;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Загрузчик задач проекта из текстового файла в кодировке UTF-8.
 *
 * <p>Формат: разделитель ';', первая строка является заголовком
 * {@code id;title;category;estimatedHours;status}.
 *
 * <p>Два вида ошибок обрабатываются по-разному:
 * <ul>
 *   <li>ошибка источника (файл отсутствует, недоступен, не читается как UTF-8)
 *       приводит к {@link DataLoadingException} с сохранённой причиной;</li>
 *   <li>ошибка записи (число, пустое поле, статус, число столбцов, заголовок)
 *       попадает в {@link LoadResult} и не останавливает чтение остальных строк.</li>
 * </ul>
 */
public class TaskFileLoader {

    private static final String SEPARATOR = ";";
    private static final String BOM = "\uFEFF";
    private static final String[] EXPECTED_HEADER = {"id", "title", "category", "estimatedHours", "status"};

    /**
     * @param path путь к файлу
     * @return корректные объекты и ошибки записей
     * @throws DataLoadingException если файл нельзя прочитать
     */
    public LoadResult load(Path path) {
        Objects.requireNonNull(path, "path");
        List<ProjectTask> tasks = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            ValidationError headerError = checkHeader(reader.readLine());
            if (headerError != null) {
                // без верного заголовка раскладка столбцов неизвестна, строки данных не разбираются
                errors.add(headerError);
                return new LoadResult(tasks, errors);
            }

            int lineNumber = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                parseRow(line, lineNumber, tasks, errors);
            }
        } catch (IOException e) {
            throw new DataLoadingException("Cannot read " + path, e);
        }
        return new LoadResult(tasks, errors);
    }

    private static ValidationError checkHeader(String headerLine) {
        if (headerLine == null) {
            return new ValidationError(1, "header", "Header is missing: the file is empty");
        }
        String text = headerLine.startsWith(BOM) ? headerLine.substring(BOM.length()) : headerLine;
        if (!Arrays.equals(splitAndStrip(text), EXPECTED_HEADER)) {
            return new ValidationError(1, "header",
                    "Expected header '" + String.join(SEPARATOR, EXPECTED_HEADER)
                            + "' but found '" + text + "'");
        }
        return null;
    }

    private static String[] splitAndStrip(String line) {
        // limit -1 сохраняет пустые поля в конце строки
        String[] fields = line.split(SEPARATOR, -1);
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].strip();
        }
        return fields;
    }

    private static void parseRow(String line, int lineNumber,
                                 List<ProjectTask> tasks, List<ValidationError> errors) {
        String[] fields = splitAndStrip(line);
        if (fields.length != EXPECTED_HEADER.length) {
            errors.add(new ValidationError(lineNumber, "columns",
                    "Expected " + EXPECTED_HEADER.length + " fields but found " + fields.length));
            return;
        }

        List<ValidationError> rowErrors = new ArrayList<>();
        String id = requireText(fields[0], "id", lineNumber, rowErrors);
        String title = requireText(fields[1], "title", lineNumber, rowErrors);
        String category = requireText(fields[2], "category", lineNumber, rowErrors);
        int estimatedHours = parsePositiveInt(fields[3], "estimatedHours", lineNumber, rowErrors);
        TaskStatus status = parseStatus(fields[4], lineNumber, rowErrors);

        // предметные ограничения проверены выше: объект создаётся только для корректной строки
        if (rowErrors.isEmpty()) {
            tasks.add(new ProjectTask(id, title, category, estimatedHours, status));
        } else {
            errors.addAll(rowErrors);
        }
    }

    private static String requireText(String text, String field, int lineNumber,
                                      List<ValidationError> rowErrors) {
        if (text.isEmpty()) {
            rowErrors.add(new ValidationError(lineNumber, field, "must not be empty"));
        }
        return text;
    }

    private static int parsePositiveInt(String text, String field, int lineNumber,
                                        List<ValidationError> rowErrors) {
        if (text.isEmpty()) {
            rowErrors.add(new ValidationError(lineNumber, field, "must not be empty"));
            return 0;
        }
        try {
            int value = Integer.parseInt(text);
            if (value <= 0) {
                rowErrors.add(new ValidationError(lineNumber, field, "must be positive: " + value));
            }
            return value;
        } catch (NumberFormatException e) {
            rowErrors.add(new ValidationError(lineNumber, field, "must be an integer: '" + text + "'"));
            return 0;
        }
    }

    private static TaskStatus parseStatus(String text, int lineNumber,
                                          List<ValidationError> rowErrors) {
        if (text.isEmpty()) {
            rowErrors.add(new ValidationError(lineNumber, "status", "must not be empty"));
            return null;
        }
        for (TaskStatus status : TaskStatus.values()) {
            if (status.name().equals(text)) {
                return status;
            }
        }
        rowErrors.add(new ValidationError(lineNumber, "status", "unknown status '" + text + "'"));
        return null;
    }
}

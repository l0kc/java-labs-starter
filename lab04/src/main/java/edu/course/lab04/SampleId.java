package edu.course.lab04;

import java.util.Objects;

/**
 * Идентификатор наблюдения: имя набора данных и номер наблюдения.
 * Неизменяемый класс, пригоден как ключ HashMap.
 * Логическое равенство определяют ОБА поля: datasetName и index.
 * Те же два поля используются в equals и в hashCode.
 */
public final class SampleId {

    private final String datasetName;
    private final int index;

    public SampleId(String datasetName, int index) {
        Objects.requireNonNull(datasetName, "datasetName");
        if (datasetName.isBlank()) {
            throw new IllegalArgumentException("Имя набора данных не может быть пустым");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Номер наблюдения не может быть отрицательным: " + index);
        }
        this.datasetName = datasetName;
        this.index = index;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SampleId other = (SampleId) o;
        return index == other.index && datasetName.equals(other.datasetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datasetName, index);
    }

    @Override
    public String toString() {
        return datasetName + "#" + index;
    }
}

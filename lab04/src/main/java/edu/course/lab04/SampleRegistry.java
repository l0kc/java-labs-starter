package edu.course.lab04;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Реестр наблюдений. Построен на композиции: внутри хранится HashMap, наследования от него нет.
 * Null в роли ключа или значения не допускается.
 */
public class SampleRegistry {

    private final Map<SampleId, DataSample> samples = new HashMap<>();

    /**
     * Добавляет наблюдение или заменяет существующее с равным ключом.
     *
     * @return прежнее значение или null, если ключа не было
     */
    public DataSample put(SampleId id, DataSample sample) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(sample, "sample");
        return samples.put(id, sample);
    }

    /**
     * @return наблюдение или null, если ключа нет
     */
    public DataSample get(SampleId id) {
        Objects.requireNonNull(id, "id");
        return samples.get(id);
    }

    /**
     * @return удалённое наблюдение или null, если ключа не было
     */
    public DataSample remove(SampleId id) {
        Objects.requireNonNull(id, "id");
        return samples.remove(id);
    }

    public boolean containsKey(SampleId id) {
        Objects.requireNonNull(id, "id");
        return samples.containsKey(id);
    }

    public int size() {
        return samples.size();
    }
}

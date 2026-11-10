package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SampleRegistryTest {

    private static final double EPS = 1e-9;

    private final SampleRegistry registry = new SampleRegistry();

    private static DataSample sample(String label) {
        return new DataSample(new double[] {1.0, 2.0}, label);
    }

    @Test
    void newRegistryIsEmpty() {
        assertEquals(0, registry.size());
    }

    @Test
    void putAndGetReturnStoredSample() {
        DataSample sample = sample("setosa");
        registry.put(new SampleId("iris", 1), sample);

        assertSame(sample, registry.get(new SampleId("iris", 1)));
        assertEquals(1, registry.size());
    }

    @Test
    void twoEqualKeyInstancesFindSameValue() {
        SampleId stored = new SampleId("iris", 42);
        SampleId lookup = new SampleId("iris", 42);
        DataSample sample = sample("setosa");

        registry.put(stored, sample);

        assertNotSame(stored, lookup);
        assertSame(sample, registry.get(lookup));
        assertTrue(registry.containsKey(lookup));
    }

    @Test
    void putWithEqualKeyReplacesValueAndKeepsSize() {
        DataSample oldSample = sample("старое");
        DataSample newSample = sample("новое");

        assertNull(registry.put(new SampleId("iris", 1), oldSample));
        DataSample previous = registry.put(new SampleId("iris", 1), newSample);

        assertSame(oldSample, previous);
        assertSame(newSample, registry.get(new SampleId("iris", 1)));
        assertEquals(1, registry.size());
    }

    @Test
    void getMissingKeyReturnsNull() {
        assertNull(registry.get(new SampleId("iris", 99)));
    }

    @Test
    void removeReturnsSampleAndDecreasesSize() {
        DataSample sample = sample("setosa");
        registry.put(new SampleId("iris", 1), sample);

        assertSame(sample, registry.remove(new SampleId("iris", 1)));
        assertEquals(0, registry.size());
        assertFalse(registry.containsKey(new SampleId("iris", 1)));
    }

    @Test
    void removeMissingKeyReturnsNullAndKeepsSize() {
        registry.put(new SampleId("iris", 1), sample("setosa"));

        assertNull(registry.remove(new SampleId("iris", 2)));
        assertEquals(1, registry.size());
    }

    @Test
    void containsKeyDistinguishesPresentAndAbsentKeys() {
        registry.put(new SampleId("iris", 1), sample("setosa"));

        assertTrue(registry.containsKey(new SampleId("iris", 1)));
        assertFalse(registry.containsKey(new SampleId("iris", 2)));
        assertFalse(registry.containsKey(new SampleId("wine", 1)));
    }

    @Test
    void unequalKeysWithSameHashCodeStoreDifferentValues() {
        // "Aa" и "BB" имеют одинаковый String.hashCode(), поэтому ключи сталкиваются
        SampleId first = new SampleId("Aa", 7);
        SampleId second = new SampleId("BB", 7);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, second);

        DataSample firstSample = sample("первое");
        DataSample secondSample = sample("второе");
        registry.put(first, firstSample);
        registry.put(second, secondSample);

        assertEquals(2, registry.size());
        assertSame(firstSample, registry.get(new SampleId("Aa", 7)));
        assertSame(secondSample, registry.get(new SampleId("BB", 7)));
    }

    @Test
    void dataSampleIsProtectedFromExternalArrayChanges() {
        double[] features = {1.0, 2.0};
        DataSample sample = new DataSample(features, "setosa");

        features[0] = 100.0;
        sample.getFeatures()[1] = 200.0;

        assertArrayEquals(new double[] {1.0, 2.0}, sample.getFeatures(), EPS);
    }

    @Test
    void nullKeyAndNullValueAreRejected() {
        assertThrows(NullPointerException.class, () -> registry.put(null, sample("x")));
        assertThrows(NullPointerException.class, () -> registry.put(new SampleId("iris", 1), null));
        assertThrows(NullPointerException.class, () -> registry.get(null));
        assertThrows(NullPointerException.class, () -> registry.remove(null));
        assertThrows(NullPointerException.class, () -> registry.containsKey(null));
    }
}

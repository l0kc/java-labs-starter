package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Учебные эксперименты с HashMap. Ключи-«нарушители» описаны только здесь, в тестах,
 * и в рабочем коде не используются.
 */
class HashMapExperimentsTest {

    /**
     * Ключ с НАМЕРЕННО одинаковым hashCode для всех объектов.
     * Контракт не нарушен (равные объекты имеют равный хеш), но все ключи сталкиваются.
     */
    private static final class CollidingKey {
        private final String name;

        CollidingKey(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            return name.equals(((CollidingKey) o).name);
        }

        @Override
        public int hashCode() {
            return 42;
        }
    }

    /**
     * Изменяемый ключ: поле id участвует в equals и hashCode и может быть изменено после put.
     * Так делать НЕЛЬЗЯ, класс нужен только для демонстрации проблемы.
     */
    private static final class MutableKey {
        private int id;

        MutableKey(int id) {
            this.id = id;
        }

        void setId(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            return id == ((MutableKey) o).id;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }

    @Test
    void collisionIsNotEquality() {
        CollidingKey first = new CollidingKey("a");
        CollidingKey second = new CollidingKey("b");

        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, second);
    }

    @Test
    void unequalKeysWithSameHashCodeKeepDifferentValues() {
        Map<CollidingKey, String> map = new HashMap<>();
        map.put(new CollidingKey("a"), "первое");
        map.put(new CollidingKey("b"), "второе");
        map.put(new CollidingKey("c"), "третье");

        assertEquals(3, map.size());
        assertEquals("первое", map.get(new CollidingKey("a")));
        assertEquals("второе", map.get(new CollidingKey("b")));
        assertEquals("третье", map.get(new CollidingKey("c")));
    }

    @Test
    void equalCollidingKeyReplacesValueInsteadOfAddingNewEntry() {
        Map<CollidingKey, String> map = new HashMap<>();
        map.put(new CollidingKey("a"), "старое");
        map.put(new CollidingKey("a"), "новое");

        assertEquals(1, map.size());
        assertEquals("новое", map.get(new CollidingKey("a")));
    }

    @Test
    void mutatingKeyAfterPutBreaksLookup() {
        Map<MutableKey, String> map = new HashMap<>();
        MutableKey key = new MutableKey(1);
        map.put(key, "значение");

        key.setId(2); // изменили поле, участвующее в hashCode

        assertEquals(1, map.size());                    // запись по-прежнему внутри
        assertNull(map.get(key));                       // но по изменённому ключу не находится
        assertFalse(map.containsKey(key));
        assertNull(map.get(new MutableKey(1)));         // и по значению, с которым ключ клали
        assertNull(map.get(new MutableKey(2)));
    }

    @Test
    void restoringMutatedKeyMakesEntryReachableAgain() {
        Map<MutableKey, String> map = new HashMap<>();
        MutableKey key = new MutableKey(1);
        map.put(key, "значение");

        key.setId(2);
        assertNull(map.get(key));

        key.setId(1);
        assertEquals("значение", map.get(key));
    }
}

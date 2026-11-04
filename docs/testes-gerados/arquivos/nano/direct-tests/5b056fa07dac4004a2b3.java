/*
 * @test
 * @summary Tests the static method comparingByKey of java.util.Map
 * @run main MapWitupTest
 */

import java.util.Map;
import java.util.Comparator;
import java.util.Map.Entry;

public class MapWitupTest {

    public static void main(String[] args) {
        testComparingByKeyWithNullComparator();
        testComparingByKeyWithNonNullComparator();
    }

    static void testComparingByKeyWithNullComparator() {
        try {
            Map.comparingByKey(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testComparingByKeyWithNonNullComparator() {
        Comparator<Integer> cmp = Integer::compare;
        Comparator<Map.Entry<Integer, String>> comparator = Map.comparingByKey(cmp);
        Entry<Integer, String> e1 = new MapEntry<>(1, "a");
        Entry<Integer, String> e2 = new MapEntry<>(2, "b");
        int result = comparator.compare(e1, e2);
        if (result >= 0) {
            throw new AssertionError("Comparator did not order entries correctly");
        }
        int reverseResult = comparator.compare(e2, e1);
        if (reverseResult <= 0) {
            throw new AssertionError("Comparator did not reverse order correctly");
        }
    }

    static class MapEntry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    }
}

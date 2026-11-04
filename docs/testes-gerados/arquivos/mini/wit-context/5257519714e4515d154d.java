/*
 * @test
 * @summary Test Map.comparingByValue(Comparator) for null argument and normal behavior
 * @run main MapWitupTest
 */

import java.util.Map;
import java.util.Comparator;
import java.util.Map.Entry;
import java.io.Serializable;

public class MapWitupTest {
    public static void main(String[] args) {
        testNullComparator();
        testComparatorBehavior();
    }

    private static void testNullComparator() {
        try {
            Map.comparingByValue(null);
            throw new Error("Expected NullPointerException for null comparator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testComparatorBehavior() {
        Comparator<Map.Entry<String, Integer>> comp = Map.comparingByValue(Comparator.naturalOrder());

        Map.Entry<String, Integer> e1 = new SimpleEntry<>("a", 1);
        Map.Entry<String, Integer> e2 = new SimpleEntry<>("b", 2);
        Map.Entry<String, Integer> e3 = new SimpleEntry<>("c", 1);

        // e1 < e2 because 1 < 2
        if (comp.compare(e1, e2) >= 0) {
            throw new Error("Comparator failed: e1 should be less than e2");
        }

        // e2 > e1
        if (comp.compare(e2, e1) <= 0) {
            throw new Error("Comparator failed: e2 should be greater than e1");
        }

        // e1 == e3 because values equal
        if (comp.compare(e1, e3) != 0) {
            throw new Error("Comparator failed: e1 should be equal to e3");
        }

        // test serializability
        if (!(comp instanceof Serializable)) {
            throw new Error("Comparator returned is not Serializable");
        }
    }

    // Minimal implementation of Map.Entry for testing
    private static class SimpleEntry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private final V value;

        SimpleEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }
    }
}

/*
 * @test
 * @summary Test Map.comparingByKey static method behavior
 * @run main MapWitupTest
 */

import java.util.Map;
import java.util.Comparator;
import java.util.AbstractMap.SimpleEntry;
import java.util.Objects;
import java.io.Serializable;

public class MapWitupTest {
    public static void main(String[] args) {
        testNullComparator();
        testComparatorOrdering();
        testComparatorSerializable();
    }

    static void testNullComparator() {
        try {
            Map.comparingByKey(null);
            throw new RuntimeException("Expected NullPointerException for null comparator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testComparatorOrdering() {
        Comparator<Map.Entry<String, Integer>> comp = Map.comparingByKey(String.CASE_INSENSITIVE_ORDER);
        Map.Entry<String, Integer> e1 = new SimpleEntry<>("a", 1);
        Map.Entry<String, Integer> e2 = new SimpleEntry<>("B", 2);

        int result1 = comp.compare(e1, e2);
        int result2 = comp.compare(e2, e1);

        if (result1 >= 0) {
            throw new RuntimeException("Expected e1 < e2 ignoring case");
        }
        if (result2 <= 0) {
            throw new RuntimeException("Expected e2 > e1 ignoring case");
        }

        // comparing equal keys
        Map.Entry<String, Integer> e3 = new SimpleEntry<>("a", 3);
        if (comp.compare(e1, e3) != 0) {
            throw new RuntimeException("Expected equal keys to compare as 0");
        }
    }

    static void testComparatorSerializable() {
        Comparator<Map.Entry<String, Integer>> comp = Map.comparingByKey(String.CASE_INSENSITIVE_ORDER);
        if (!(comp instanceof Serializable)) {
            throw new RuntimeException("Comparator returned by comparingByKey should be Serializable");
        }
    }
}

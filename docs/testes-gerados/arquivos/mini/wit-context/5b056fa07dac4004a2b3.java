/*
 * @test
 * @summary Test Map.comparingByKey static method behavior
 * @run main MapWitupTest
 */

import java.util.Comparator;
import java.util.Map;
import java.io.Serializable;

public class MapWitupTest {
    public static void main(String[] args) {
        testComparingByKeyWithValidComparator();
        testComparingByKeyWithNullComparator();
    }

    private static void testComparingByKeyWithValidComparator() {
        Comparator<Map.Entry<String, Integer>> comp = Map.comparingByKey(String.CASE_INSENSITIVE_ORDER);
        // Create two Map.Entry instances using anonymous classes
        Map.Entry<String, Integer> e1 = new Map.Entry<String, Integer>() {
            public String getKey() { return "a"; }
            public Integer getValue() { return 1; }
            public Integer setValue(Integer value) { throw new UnsupportedOperationException(); }
        };
        Map.Entry<String, Integer> e2 = new Map.Entry<String, Integer>() {
            public String getKey() { return "B"; }
            public Integer getValue() { return 2; }
            public Integer setValue(Integer value) { throw new UnsupportedOperationException(); }
        };
        int result = comp.compare(e1, e2);
        // Since "a" case-insensitive is less than "B", result should be negative
        if (result >= 0) {
            throw new AssertionError("Expected negative result for comparing 'a' and 'B' ignoring case, got: " + result);
        }
    }

    private static void testComparingByKeyWithNullComparator() {
        try {
            Map.comparingByKey(null);
            throw new AssertionError("Expected NullPointerException when passing null comparator");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}

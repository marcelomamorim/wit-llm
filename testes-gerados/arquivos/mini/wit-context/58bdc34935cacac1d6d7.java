/*
 * @test
 * @summary Deterministic tests for java.util.TreeMap.containsKey(Object) method
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testContainsKeyWithNullKeyAndNullComparator();
        testContainsKeyWithNonNullKey();
        testContainsKeyWithEmptyMap();
    }

    private static void testContainsKeyWithNullKeyAndNullComparator() {
        TreeMap<String, String> map = new TreeMap<>();
        // Insert a key to ensure map is not empty
        map.put("key", "value");
        boolean result = false;
        try {
            // According to WIT, containsKey(null) with natural ordering (null comparator) throws NPE
            result = map.containsKey(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        }
        // Defensive check: result should not be true
        if (result) {
            throw new RuntimeException("containsKey(null) returned true unexpectedly");
        }
    }

    private static void testContainsKeyWithNonNullKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "alpha");
        map.put("b", "beta");
        if (!map.containsKey("a")) {
            throw new RuntimeException("containsKey failed for existing key 'a'");
        }
        if (map.containsKey("c")) {
            throw new RuntimeException("containsKey returned true for non-existing key 'c'");
        }
    }

    private static void testContainsKeyWithEmptyMap() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        if (map.containsKey(1)) {
            throw new RuntimeException("containsKey returned true on empty map");
        }
    }
}

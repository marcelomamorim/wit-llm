/*
 * @test
 * @summary Deterministic tests for java.util.TreeMap.get(Object) method
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testGetWithNullKeyAndNullComparator();
        testGetWithNonNullKey();
        testGetWithAbsentKey();
    }

    private static void testGetWithNullKeyAndNullComparator() {
        TreeMap<String, String> map = new TreeMap<>();
        // Insert a key-value pair to avoid empty map
        map.put("key", "value");
        try {
            // get(null) with natural ordering comparator (null) should throw NullPointerException
            map.get(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetWithNonNullKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "alpha");
        map.put("b", "beta");
        String v = map.get("a");
        if (!"alpha".equals(v)) {
            throw new RuntimeException("Expected value 'alpha' for key 'a', got: " + v);
        }
    }

    private static void testGetWithAbsentKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("x", "ex");
        // get with a key not present returns null
        String v = map.get("y");
        if (v != null) {
            throw new RuntimeException("Expected null for absent key 'y', got: " + v);
        }
    }
}

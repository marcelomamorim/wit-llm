/*
 * @test
 * @summary Test ConcurrentHashMap.contains(Object) method
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        // Test contains on empty map
        if (map.contains("value")) {
            throw new RuntimeException("contains returned true on empty map");
        }

        // Put a key-value pair and test contains for existing value
        map.put("key1", "value1");
        if (!map.contains("value1")) {
            throw new RuntimeException("contains returned false for existing value");
        }

        // Test contains for non-existing value
        if (map.contains("value2")) {
            throw new RuntimeException("contains returned true for non-existing value");
        }

        // Test contains for null value (ConcurrentHashMap does not allow null values, so should be false)
        if (map.contains(null)) {
            throw new RuntimeException("contains returned true for null value");
        }

        // Add multiple entries and test contains for one of them
        map.put("key2", "value2");
        map.put("key3", "value3");
        if (!map.contains("value3")) {
            throw new RuntimeException("contains returned false for existing value among multiple entries");
        }

        // Test contains for a value that equals an existing value but is a different object
        String v = new String("value1");
        if (!map.contains(v)) {
            throw new RuntimeException("contains returned false for equal but distinct value object");
        }

        System.out.println("All tests passed.");
    }
}

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
            throw new Error("Test failed: contains returned true on empty map");
        }

        // Put a key-value pair and test contains for existing value
        map.put("key1", "value1");
        if (!map.contains("value1")) {
            throw new Error("Test failed: contains returned false for existing value");
        }

        // Test contains for non-existing value
        if (map.contains("value2")) {
            throw new Error("Test failed: contains returned true for non-existing value");
        }

        // Put null value and test contains for null
        map.put("key2", null);
        if (!map.contains(null)) {
            throw new Error("Test failed: contains returned false for null value");
        }

        // Test contains with null argument on map without null values
        ConcurrentHashMap<String, String> map2 = new ConcurrentHashMap<>();
        if (map2.contains(null)) {
            throw new Error("Test failed: contains returned true for null on map without null values");
        }

        System.out.println("All tests passed.");
    }
}

/*
 * @test
 * @summary Test ConcurrentHashMap.contains(Object) method behavior including null input
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        // Test contains with existing value
        if (!map.contains("value1")) {
            throw new AssertionError("Expected contains to return true for existing value");
        }

        // Test contains with non-existing value
        if (map.contains("value3")) {
            throw new AssertionError("Expected contains to return false for non-existing value");
        }

        // Test contains with null value - expect NullPointerException
        try {
            map.contains(null);
            throw new AssertionError("Expected NullPointerException when calling contains with null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}

/*
 * @test
 * @summary Test ConcurrentHashMap.contains(Object) method behavior including null argument
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

        // Test contains with null argument - should throw NullPointerException
        boolean npeThrown = false;
        try {
            map.contains(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when calling contains(null)");
        }

        // Test contains with an object not of value type
        if (map.contains(new Object())) {
            throw new AssertionError("Expected contains to return false for unrelated object");
        }
    }
}

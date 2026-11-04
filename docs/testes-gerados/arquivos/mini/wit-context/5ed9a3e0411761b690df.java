/*
 * @test
 * @summary Test ConcurrentSkipListMap.remove(Object key, Object value) for null key and value matching
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test that remove throws NullPointerException if key is null
        boolean npeThrown = false;
        try {
            map.remove(null, "value");
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when key is null");
        }

        // Test that remove returns false if value is null (no removal)
        map.put("key1", "value1");
        boolean removed = map.remove("key1", null);
        if (removed) {
            throw new AssertionError("Expected remove to return false when value is null");
        }

        // Test that remove returns false if key-value pair does not exist
        removed = map.remove("key1", "wrongValue");
        if (removed) {
            throw new AssertionError("Expected remove to return false when key-value pair does not exist");
        }

        // Test that remove returns true and removes the entry if key-value pair exists
        removed = map.remove("key1", "value1");
        if (!removed) {
            throw new AssertionError("Expected remove to return true when key-value pair exists");
        }

        // Confirm the map no longer contains the key
        if (map.containsKey("key1")) {
            throw new AssertionError("Expected key to be removed from map");
        }
    }
}

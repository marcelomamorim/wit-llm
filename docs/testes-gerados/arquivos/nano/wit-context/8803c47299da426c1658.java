/*
 * @test
 * @summary Test for ConcurrentHashMap.remove(Object o)
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithExistingEntry();
        testRemoveWithNonExistingEntry();
        testRemoveWithNullEntry();
    }

    private static void testRemoveWithExistingEntry() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        Map.Entry<String, String> entry = new java.util.AbstractMap.SimpleEntry<>("key1", "value1");
        boolean result = map.remove(entry);
        if (!result) {
            throw new AssertionError("Expected remove to return true for existing entry");
        }
        if (map.containsKey("key1")) {
            throw new AssertionError("Map should not contain 'key1' after removal");
        }
    }

    private static void testRemoveWithNonExistingEntry() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key2", "value2");
        Map.Entry<String, String> entry = new java.util.AbstractMap.SimpleEntry<>("nonexistent", "value");
        boolean result = map.remove(entry);
        if (result) {
            throw new AssertionError("Expected remove to return false for non-existing entry");
        }
        if (!map.containsKey("key2")) {
            throw new AssertionError("Map should still contain 'key2' after failed removal");
        }
    }

    private static void testRemoveWithNullEntry() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key3", "value3");
        boolean result = false;
        try {
            result = map.remove((Map.Entry<String, String>) null);
        } catch (NullPointerException e) {
            // Expected exception
        }
        if (result) {
            throw new AssertionError("Expected remove to return false or throw for null entry");
        }
        if (!map.containsKey("key3")) {
            throw new AssertionError("Map should still contain 'key3' after null removal attempt");
        }
    }
}

/* @test    @summary Test remove method for ConcurrentHashMap with null key handling    @run main ConcurrentHashMapWitupTest_P1f47da849173 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_P1f47da849173 {

    public static void main(String[] args) {
        testRemoveWithNullKey();
        testRemoveWithExistingKeyAndValue();
        testRemoveWithExistingKeyAndDifferentValue();
        testRemoveWithNonExistingKey();
    }

    private static void testRemoveWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean exceptionThrown = false;
        try {
            map.remove(null, "value");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when removing with null key");
        }
    }

    private static void testRemoveWithExistingKeyAndValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = map.remove("key1", "value1");
        if (!result) {
            throw new AssertionError("Expected remove to return true for existing key and matching value");
        }
        if (map.containsKey("key1")) {
            throw new AssertionError("Map should not contain key after removal");
        }
    }

    private static void testRemoveWithExistingKeyAndDifferentValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key2", "value2");
        boolean result = map.remove("key2", "differentValue");
        if (result) {
            throw new AssertionError("Expected remove to return false for existing key and non-matching value");
        }
        if (!map.containsKey("key2")) {
            throw new AssertionError("Map should still contain key after failed removal");
        }
    }

    private static void testRemoveWithNonExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean result = map.remove("nonExistingKey", "value");
        if (result) {
            throw new AssertionError("Expected remove to return false for non-existing key");
        }
    }
}

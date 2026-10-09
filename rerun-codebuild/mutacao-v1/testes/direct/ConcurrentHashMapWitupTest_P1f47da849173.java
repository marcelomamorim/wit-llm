/* @test    @summary Test ConcurrentHashMap.remove(Object key, Object value) for correctness and exception handling    @run main ConcurrentHashMapWitupTest_P1f47da849173 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_P1f47da849173 {

    public static void main(String[] args) {
        testRemoveExistingKeyAndValue();
        testRemoveExistingKeyWithDifferentValue();
        testRemoveNullKey();
        testRemoveNullValue();
        testRemoveNonExistingKey();
    }

    static void testRemoveExistingKeyAndValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = map.remove("key1", "value1");
        if (!result) {
            throw new AssertionError("Expected true when removing existing key with matching value");
        }
        if (map.containsKey("key1")) {
            throw new AssertionError("Map should not contain 'key1' after removal");
        }
    }

    static void testRemoveExistingKeyWithDifferentValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key2", "value2");
        boolean result = map.remove("key2", "differentValue");
        if (result) {
            throw new AssertionError("Expected false when removing with non-matching value");
        }
        if (!map.containsKey("key2")) {
            throw new AssertionError("Map should still contain 'key2' after failed removal");
        }
    }

    static void testRemoveNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.remove(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testRemoveNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key3", "value3");
        boolean result = map.remove("key3", null);
        if (result) {
            throw new AssertionError("Expected false when removing with null value");
        }
        if (!map.containsKey("key3")) {
            throw new AssertionError("Map should still contain 'key3' after failed removal");
        }
    }

    static void testRemoveNonExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean result = map.remove("nonexistent", "value");
        if (result) {
            throw new AssertionError("Expected false when removing non-existing key");
        }
    }
}

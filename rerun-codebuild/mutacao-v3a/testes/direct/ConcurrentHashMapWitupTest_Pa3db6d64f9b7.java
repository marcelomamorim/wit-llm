/* @test    @summary Test ConcurrentHashMap remove(Object key, Object value) method for correctness and exceptions    @run main ConcurrentHashMapWitupTest_Pa3db6d64f9b7 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_Pa3db6d64f9b7 {
    public static void main(String[] args) {
        testRemoveWithExistingKeyAndMatchingValue();
        testRemoveWithExistingKeyAndNonMatchingValue();
        testRemoveWithNullKey();
        testRemoveWithNullValue();
        testRemoveWithAbsentKey();
    }

    static void testRemoveWithExistingKeyAndMatchingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String key = "key1";
        String value = "value1";
        map.put(key, value);
        boolean removed = map.remove(key, value);
        if (!removed) {
            throw new AssertionError("Expected true when removing existing key with matching value");
        }
        if (map.containsKey(key)) {
            throw new AssertionError("Map should not contain key after removal");
        }
    }

    static void testRemoveWithExistingKeyAndNonMatchingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String key = "key2";
        String value1 = "value2";
        String value2 = "different";
        map.put(key, value1);
        boolean removed = map.remove(key, value2);
        if (removed) {
            throw new AssertionError("Expected false when value does not match for existing key");
        }
        if (!map.containsKey(key)) {
            throw new AssertionError("Map should still contain key after failed removal");
        }
        if (!map.get(key).equals(value1)) {
            throw new AssertionError("Value associated with key should remain unchanged");
        }
    }

    static void testRemoveWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.remove(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // test passes
        }
    }

    static void testRemoveWithNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        try {
            map.remove("key", null);
            throw new AssertionError("Expected no exception but removal should return false");
        } catch (NullPointerException e) {
            throw new AssertionError("Unexpected NullPointerException for null value", e);
        }
        boolean result = map.remove("key", null);
        if (result) {
            throw new AssertionError("Removal with null value should return false");
        }
    }

    static void testRemoveWithAbsentKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean removed = map.remove("absentKey", "someValue");
        if (removed) {
            throw new AssertionError("Removing absent key should return false");
        }
    }
}

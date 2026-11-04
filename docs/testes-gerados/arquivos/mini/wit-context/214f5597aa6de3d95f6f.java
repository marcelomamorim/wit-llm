/*
 * @test
 * @summary Test ConcurrentHashMap.remove(Object key, Object value) for null key and value matching
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testRemoveNullKeyThrows();
        testRemoveWithNullValueReturnsFalse();
        testRemoveWithNonMatchingValueReturnsFalse();
        testRemoveWithMatchingValueReturnsTrue();
    }

    private static void testRemoveNullKeyThrows() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.remove(null, "value");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveWithNullValueReturnsFalse() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        boolean removed = map.remove("key", null);
        if (removed) {
            throw new RuntimeException("Expected remove to return false when value is null");
        }
    }

    private static void testRemoveWithNonMatchingValueReturnsFalse() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        boolean removed = map.remove("key", "otherValue");
        if (removed) {
            throw new RuntimeException("Expected remove to return false when value does not match");
        }
        if (!map.containsKey("key")) {
            throw new RuntimeException("Key should still be present after failed remove");
        }
    }

    private static void testRemoveWithMatchingValueReturnsTrue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        boolean removed = map.remove("key", "value");
        if (!removed) {
            throw new RuntimeException("Expected remove to return true when value matches");
        }
        if (map.containsKey("key")) {
            throw new RuntimeException("Key should be removed after successful remove");
        }
    }
}

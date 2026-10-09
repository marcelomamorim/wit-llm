/*
 * @test
 * @summary Test ConcurrentSkipListMap remove(Object key, Object value) method
 * @run main ConcurrentSkipListMapWitupTest_P0c27430e6b59
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P0c27430e6b59 {
    public static void main(String[] args) {
        testRemoveNullKeyThrows();
        testRemoveNullValueReturnsFalse();
        testRemoveNonMatchingValueReturnsFalse();
        testRemoveMatchingValueReturnsTrue();
    }

    static void testRemoveNullKeyThrows() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean thrown = false;
        try {
            map.remove(null, "value");
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null key");
        }
    }

    static void testRemoveNullValueReturnsFalse() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "value");
        boolean removed = map.remove("key", null);
        if (removed) {
            throw new AssertionError("Expected remove to return false when value is null");
        }
        if (!map.containsKey("key")) {
            throw new AssertionError("Map should still contain the key after remove with null value");
        }
    }

    static void testRemoveNonMatchingValueReturnsFalse() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "value");
        boolean removed = map.remove("key", "otherValue");
        if (removed) {
            throw new AssertionError("Expected remove to return false when value does not match");
        }
        if (!map.containsKey("key")) {
            throw new AssertionError("Map should still contain the key after remove with non-matching value");
        }
    }

    static void testRemoveMatchingValueReturnsTrue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "value");
        boolean removed = map.remove("key", "value");
        if (!removed) {
            throw new AssertionError("Expected remove to return true when value matches");
        }
        if (map.containsKey("key")) {
            throw new AssertionError("Map should not contain the key after successful remove");
        }
    }
}

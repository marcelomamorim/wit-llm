/*
 * @test
 * @summary Test ConcurrentHashMap remove(Object key, Object value) method
 * @run main ConcurrentHashMapWitupTest_Pb4ea6d699b7d
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_Pb4ea6d699b7d {
    public static void main(String[] args) {
        testRemoveWithNullKey();
        testRemoveWithNullValue();
        testRemoveWithAbsentKey();
        testRemoveWithPresentKeyAndMatchingValue();
        testRemoveWithPresentKeyAndNonMatchingValue();
    }

    static void testRemoveWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
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

    static void testRemoveWithNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        // remove with null value should return false and not remove
        boolean removed = map.remove("key", null);
        if (removed) {
            throw new AssertionError("remove with null value should return false");
        }
        if (!"value".equals(map.get("key"))) {
            throw new AssertionError("Value should not be removed when value argument is null");
        }
    }

    static void testRemoveWithAbsentKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        // remove with absent key should return false
        boolean removed = map.remove("absent", "value");
        if (removed) {
            throw new AssertionError("remove with absent key should return false");
        }
        if (!"value".equals(map.get("key"))) {
            throw new AssertionError("Existing value should remain unchanged");
        }
    }

    static void testRemoveWithPresentKeyAndMatchingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        // remove with matching value should return true and remove the entry
        boolean removed = map.remove("key", "value");
        if (!removed) {
            throw new AssertionError("remove with matching value should return true");
        }
        if (map.containsKey("key")) {
            throw new AssertionError("Entry should be removed when value matches");
        }
    }

    static void testRemoveWithPresentKeyAndNonMatchingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        // remove with non-matching value should return false and not remove
        boolean removed = map.remove("key", "other");
        if (removed) {
            throw new AssertionError("remove with non-matching value should return false");
        }
        if (!"value".equals(map.get("key"))) {
            throw new AssertionError("Value should not be removed when value does not match");
        }
    }
}

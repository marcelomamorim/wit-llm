/*
 * @test
 * @summary Tests for ConcurrentHashMap.remove(Object, Object)
 * @run main ConcurrentHashMapWitupTest_Pa3db6d64f9b7
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_Pa3db6d64f9b7 {

    public static void main(String[] args) {
        testRemoveWithNullKey();
        testRemoveWithExistingKeyAndValue();
        testRemoveWithNonExistingKey();
        testRemoveWithNullValue();
    }

    private static void testRemoveWithNullKey() {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        boolean exceptionThrown = false;
        try {
            map.remove(null, "value");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null key");
        }
    }

    private static void testRemoveWithExistingKeyAndValue() {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        Object key = "key";
        Object value = "value";
        map.put(key, value);
        boolean result = map.remove(key, value);
        if (result != true) {
            throw new AssertionError("Expected remove to return true for existing key and value");
        }
        if (map.containsKey(key)) {
            throw new AssertionError("Map should not contain key after removal");
        }
    }

    private static void testRemoveWithNonExistingKey() {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        Object key = "nonexistent";
        Object value = "value";
        boolean result = map.remove(key, value);
        if (result != false) {
            throw new AssertionError("Expected remove to return false for non-existing key");
        }
    }

    private static void testRemoveWithNullValue() {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        Object key = "key";
        map.put(key, "value");
        boolean result = map.remove(key, null);
        if (result != false) {
            throw new AssertionError("Expected remove to return false when value is null");
        }
    }
}

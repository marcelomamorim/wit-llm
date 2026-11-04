/*
 * @test
 * @summary Test ConcurrentHashMap.remove(Object, Object) for null key behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithNullKey();
        testRemoveWithNonNullKeyAndValue();
        testRemoveWithNonNullKeyAndNullValue();
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
            throw new AssertionError("Expected NullPointerException when removing with null key");
        }
    }

    private static void testRemoveWithNonNullKeyAndValue() {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        Object key = "key";
        Object value = "value";
        map.put(key, value);
        boolean result = map.remove(key, value);
        if (!result) {
            throw new AssertionError("Expected remove to return true for existing key-value pair");
        }
        if (map.containsKey(key)) {
            throw new AssertionError("Map should not contain key after removal");
        }
    }

    private static void testRemoveWithNonNullKeyAndNullValue() {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        Object key = "key";
        Object value = "value";
        map.put(key, value);
        boolean result = map.remove(key, null);
        if (result) {
            throw new AssertionError("Expected remove to return false when value does not match");
        }
        if (!map.containsKey(key)) {
            throw new AssertionError("Map should still contain key after failed removal");
        }
    }
}

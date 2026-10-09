/*
 * @test
 * @summary Test for ConcurrentSkipListMap.remove(Object, Object) method, including null key handling
 * @run main ConcurrentSkipListMapWitupTest_P4c6ff8faccbd
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P4c6ff8faccbd {

    public static void main(String[] args) {
        testRemoveWithNullKey();
        testRemoveWithNonNullKeyAndValue();
        testRemoveWithNonExistingKey();
    }

    private static void testRemoveWithNullKey() {
        ConcurrentSkipListMap<Object, Object> map = new ConcurrentSkipListMap<>();
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
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        boolean result = map.remove("key1", "value1");
        if (!result) {
            throw new AssertionError("Expected remove to return true for existing key-value pair");
        }
        if (map.containsKey("key1")) {
            throw new AssertionError("Map should not contain key after removal");
        }
    }

    private static void testRemoveWithNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key2", "value2");
        boolean result = map.remove("nonexistent", "value");
        if (result) {
            throw new AssertionError("Expected remove to return false for non-existing key");
        }
    }
}

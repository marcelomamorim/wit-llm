/*
 * @test
 * @summary Test remove method of ConcurrentSkipListMap for null key handling
 * @run main ConcurrentSkipListMapWitupTest_P71037d3a23e7
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P71037d3a23e7 {

    public static void main(String[] args) {
        testRemoveNullKey();
        testRemoveValidKeyAndValue();
        testRemoveNullValue();
    }

    private static void testRemoveNullKey() {
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

    private static void testRemoveValidKeyAndValue() {
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

    private static void testRemoveNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key2", "value2");
        boolean result = map.remove("key2", null);
        if (result) {
            throw new AssertionError("Remove with null value should return false");
        }
        if (!map.containsKey("key2")) {
            throw new AssertionError("Map should still contain key after failed removal");
        }
    }
}

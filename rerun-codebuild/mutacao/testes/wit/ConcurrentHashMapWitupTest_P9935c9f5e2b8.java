/*
 * @test
 * @summary Test for ConcurrentHashMap.containsValue(Object)
 * @run main ConcurrentHashMapWitupTest_P9935c9f5e2b8
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_P9935c9f5e2b8 {

    public static void main(String[] args) {
        testContainsValueWithNull();
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
    }

    private static void testContainsValueWithNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean threwException = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException for null value");
        }
    }

    private static void testContainsValueWithExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = map.containsValue("value1");
        if (!result) {
            throw new AssertionError("Expected true for existing value");
        }
    }

    private static void testContainsValueWithNonExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = map.containsValue("value2");
        if (result) {
            throw new AssertionError("Expected false for non-existing value");
        }
    }
}

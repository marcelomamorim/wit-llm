/*
 * @test    @summary Test for ConcurrentHashMap.containsValue(Object)
 * @run main ConcurrentHashMapWitupTest_P9935c9f5e2b8
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_P9935c9f5e2b8 {

    public static void main(String[] args) {
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
        testContainsValueWithNullThrows();
    }

    static void testContainsValueWithExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = map.containsValue("value1");
        if (!result) {
            throw new AssertionError("Expected true for existing value");
        }
    }

    static void testContainsValueWithNonExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = map.containsValue("value2");
        if (result) {
            throw new AssertionError("Expected false for non-existing value");
        }
    }

    static void testContainsValueWithNullThrows() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean threw = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException for null argument");
        }
    }
}

/* @test    @summary Test for ConcurrentHashMap.containsValue(Object) method    @run main ConcurrentHashMapWitupTest_Pe0d1363253fe */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_Pe0d1363253fe {
    public static void main(String[] args) {
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
        testContainsValueWithNullThrows();
    }

    static void testContainsValueWithExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        boolean result = map.containsValue("value1");
        if (!result) {
            throw new AssertionError("Expected true for existing value");
        }
    }

    static void testContainsValueWithNonExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        boolean result = map.containsValue("nonexistent");
        if (result) {
            throw new AssertionError("Expected false for non-existing value");
        }
    }

    static void testContainsValueWithNullThrows() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.containsValue(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // expected
        }
    }
}

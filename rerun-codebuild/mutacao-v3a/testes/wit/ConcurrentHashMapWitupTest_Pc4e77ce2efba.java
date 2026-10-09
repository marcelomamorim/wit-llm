/*
 * @test
 * @summary Test for ConcurrentHashMap.containsValue(Object value)
 * @run main ConcurrentHashMapWitupTest_Pc4e77ce2efba
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_Pc4e77ce2efba {

    public static void main(String[] args) {
        testContainsValueWithNull();
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
    }

    public static void testContainsValueWithNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean threwException = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when calling containsValue(null)");
        }
    }

    public static void testContainsValueWithExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String key = "key";
        String value = "value";
        map.put(key, value);
        boolean result = map.containsValue(value);
        if (!result) {
            throw new AssertionError("containsValue should return true for existing value")
        }
    }

    public static void testContainsValueWithNonExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        boolean result = map.containsValue("nonexistent");
        if (result) {
            throw new AssertionError("containsValue should return false for non-existing value")
        }
    }
}

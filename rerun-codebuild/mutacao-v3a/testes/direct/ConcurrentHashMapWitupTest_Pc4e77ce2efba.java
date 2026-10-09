/* @test    @summary Test for ConcurrentHashMap.containsValue method   @run main ConcurrentHashMapWitupTest_Pc4e77ce2efba */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_Pc4e77ce2efba {

    public static void main(String[] args) {
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
        testContainsValueWithNullThrows();
    }

    private static void testContainsValueWithExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        boolean result = map.containsValue("value1");
        if (!result) {
            throw new AssertionError("Expected true for existing value");
        }
    }

    private static void testContainsValueWithNonExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        boolean result = map.containsValue("nonexistent");
        if (result) {
            throw new AssertionError("Expected false for non-existing value");
        }
    }

    private static void testContainsValueWithNullThrows() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean threw = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when passing null");
        }
    }
}

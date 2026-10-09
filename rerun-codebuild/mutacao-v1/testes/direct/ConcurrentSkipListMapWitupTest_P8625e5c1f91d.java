/* @test    @summary Test containsValue method of ConcurrentSkipListMap for basic behavior and null handling    @run main ConcurrentSkipListMapWitupTest_P8625e5c1f91d */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P8625e5c1f91d {

    public static void main(String[] args) {
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
        testContainsValueWithNullThrows();
    }

    static void testContainsValueWithExistingValue() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        boolean result = map.containsValue(2);
        if (!result) {
            throw new AssertionError("Expected containsValue to return true for existing value 2");
        }
    }

    static void testContainsValueWithNonExistingValue() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("x", 10);
        map.put("y", 20);
        boolean result = map.containsValue(30);
        if (result) {
            throw new AssertionError("Expected containsValue to return false for non-existing value 30");
        }
    }

    static void testContainsValueWithNullThrows() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("foo", 100);
        boolean threw = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when calling containsValue with null");
        }
    }
}

/*
 * @test
 * @summary Test ConcurrentSkipListMap.containsValue for various cases
 * @run main ConcurrentSkipListMapWitupTest_P7906830e685f
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P7906830e685f {
    public static void main(String[] args) {
        testContainsValueWithNull();
        testContainsValueWithExisting();
        testContainsValueWithNonExisting();
    }

    static void testContainsValueWithNull() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null value");
        }
    }

    static void testContainsValueWithExisting() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        boolean result1 = map.containsValue("value1");
        boolean result2 = map.containsValue("value2");
        if (!result1 || !result2) {
            throw new AssertionError("Expected containsValue to return true for existing values");
        }
    }

    static void testContainsValueWithNonExisting() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        boolean result = map.containsValue("nonexistent");
        if (result) {
            throw new AssertionError("Expected containsValue to return false for non-existing value");
        }
    }
}

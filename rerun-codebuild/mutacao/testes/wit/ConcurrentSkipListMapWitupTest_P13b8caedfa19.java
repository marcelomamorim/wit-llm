/*
 * @test
 * @summary Test for containsValue method in ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_P13b8caedfa19
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P13b8caedfa19 {

    public static void main(String[] args) {
        testContainsValueWithNull();
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
    }

    private static void testContainsValueWithNull() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
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

    private static void testContainsValueWithExistingValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key1";
        String value = "value1";
        map.put(key, value);
        boolean result = map.containsValue(value);
        if (!result) {
            throw new AssertionError("containsValue should return true for existing value")
        }
    }

    private static void testContainsValueWithNonExistingValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        boolean result = map.containsValue("nonexistent");
        if (result) {
            throw new AssertionError("containsValue should return false for non-existing value")
        }
    }
}

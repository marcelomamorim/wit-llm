/*
 * @test
 * @summary Test ConcurrentSkipListMap.containsValue method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testContainsValueWithNullThrows();
        testContainsValueOnEmptyMap();
        testContainsValueWithPresentValue();
        testContainsValueWithAbsentValue();
    }

    static void testContainsValueWithNullThrows() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.containsValue(null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testContainsValueOnEmptyMap() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        if (map.containsValue("anyValue")) {
            throw new AssertionError("containsValue should return false on empty map");
        }
    }

    static void testContainsValueWithPresentValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        if (!map.containsValue("value1")) {
            throw new AssertionError("containsValue should return true for present value 'value1'");
        }
        if (!map.containsValue("value2")) {
            throw new AssertionError("containsValue should return true for present value 'value2'");
        }
    }

    static void testContainsValueWithAbsentValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        if (map.containsValue("absentValue")) {
            throw new AssertionError("containsValue should return false for absent value");
        }
    }
}

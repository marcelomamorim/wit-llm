/*
 * @test
 * @summary Test ConcurrentSkipListMap.containsValue behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testContainsValueWithNullThrows();
        testContainsValueReturnsTrueForPresentValue();
        testContainsValueReturnsFalseForAbsentValue();
        testContainsValueWithNullValueInMap();
    }

    static void testContainsValueWithNullThrows() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean thrown = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null argument");
        }
    }

    static void testContainsValueReturnsTrueForPresentValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        if (!map.containsValue("value1")) {
            throw new AssertionError("containsValue should return true for present value");
        }
        if (!map.containsValue("value2")) {
            throw new AssertionError("containsValue should return true for present value");
        }
    }

    static void testContainsValueReturnsFalseForAbsentValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        if (map.containsValue("absent")) {
            throw new AssertionError("containsValue should return false for absent value");
        }
    }

    static void testContainsValueWithNullValueInMap() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", null);
        // containsValue(null) should throw NullPointerException regardless of null values in map
        boolean thrown = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null argument even if map contains null values");
        }
    }
}

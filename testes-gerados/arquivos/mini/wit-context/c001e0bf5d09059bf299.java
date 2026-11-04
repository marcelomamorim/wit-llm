/*
 * @test
 * @summary Test java.util.TreeMap.headMap(K toKey, boolean inclusive) for range checks and exception
 * @run main TreeMapWitupTest
 */

import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testHeadMapRangeCheck();
        testHeadMapReturnsDescendingSubMap();
        System.out.println("All tests passed.");
    }

    static void testHeadMapRangeCheck() {
        TreeMap<Integer, String> map = new TreeMap<>();
        // Populate map with some keys
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        // toKey in range: should not throw
        NavigableMap<Integer, String> headMap = map.headMap(20, true);
        if (!headMap.containsKey(10) || !headMap.containsKey(20)) {
            throw new AssertionError("headMap should contain keys <= 20 inclusive");
        }

        // toKey out of range: should throw IllegalArgumentException
        // The exact condition for out of range is internal, but we can test a key less than first key
        // or greater than last key depending on map state.
        // Since the map is full range, headMap with toKey < first key should throw.
        try {
            map.headMap(5, true); // 5 < 10 (lowest key)
            throw new AssertionError("Expected IllegalArgumentException for toKey out of range");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Also test with inclusive = false, toKey = 10 (lowest key), should not throw
        map.headMap(10, false);

        // Test with inclusive = false and toKey < first key, should throw
        try {
            map.headMap(9, false);
            throw new AssertionError("Expected IllegalArgumentException for toKey out of range with inclusive=false");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testHeadMapReturnsDescendingSubMap() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        NavigableMap<Integer, String> headMap = map.headMap(3, true);

        // The returned map should be a NavigableMap and contain keys less or equal to 3
        if (!(headMap instanceof NavigableMap)) {
            throw new AssertionError("headMap should return a NavigableMap");
        }

        for (Integer key : headMap.keySet()) {
            if (key > 3) {
                throw new AssertionError("headMap contains key greater than toKey");
            }
        }
    }
}

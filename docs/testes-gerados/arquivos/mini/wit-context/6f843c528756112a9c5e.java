/*
 * @test
 * @summary Test java.util.TreeMap.tailMap(K fromKey, boolean inclusive) for range checks and correct submap creation
 * @run main TreeMapWitupTest
 */

import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testTailMapWithinRange();
        testTailMapOutOfRangeThrows();
    }

    private static void testTailMapWithinRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        // tailMap fromKey in range, inclusive true
        NavigableMap<Integer, String> tailInclusive = map.tailMap(3, true);
        if (!tailInclusive.containsKey(3) || !tailInclusive.containsKey(5)) {
            throw new AssertionError("tailMap inclusive should contain keys >= 3");
        }

        // tailMap fromKey in range, inclusive false
        NavigableMap<Integer, String> tailExclusive = map.tailMap(3, false);
        if (tailExclusive.containsKey(3) || !tailExclusive.containsKey(5)) {
            throw new AssertionError("tailMap exclusive should contain keys > 3 only");
        }
    }

    private static void testTailMapOutOfRangeThrows() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");

        // fromKey less than lowest key in map, should throw IllegalArgumentException
        try {
            map.tailMap(5, false);
            throw new AssertionError("Expected IllegalArgumentException for fromKey out of range");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // fromKey less than lowest key in map, inclusive true also throws
        try {
            map.tailMap(5, true);
            throw new AssertionError("Expected IllegalArgumentException for fromKey out of range");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}

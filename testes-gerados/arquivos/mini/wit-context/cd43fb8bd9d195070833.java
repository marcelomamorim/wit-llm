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

        // tailMap fromKey in range inclusive true
        NavigableMap<Integer, String> tailInclusive = map.tailMap(3, true);
        if (!tailInclusive.containsKey(3) || !tailInclusive.containsKey(5)) {
            throw new AssertionError("tailMap inclusive=true should include fromKey and higher keys");
        }

        // tailMap fromKey in range inclusive false
        NavigableMap<Integer, String> tailExclusive = map.tailMap(3, false);
        if (tailExclusive.containsKey(3) || !tailExclusive.containsKey(5)) {
            throw new AssertionError("tailMap inclusive=false should exclude fromKey but include higher keys");
        }
    }

    private static void testTailMapOutOfRangeThrows() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");

        // The tailMap method should throw IllegalArgumentException if fromKey is out of range
        // According to source, if !inRange(fromKey, inclusive) then throw
        // We test with a fromKey less than the lowest key (10) and inclusive false
        try {
            map.tailMap(5, false);
            throw new AssertionError("Expected IllegalArgumentException for fromKey out of range");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Also test with inclusive true but fromKey less than lowest key should NOT throw
        // Because inRange should be true in that case
        try {
            NavigableMap<Integer, String> tail = map.tailMap(10, true);
            if (!tail.containsKey(10)) {
                throw new AssertionError("tailMap inclusive=true fromKey equal to lowest key should include fromKey");
            }
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Did not expect IllegalArgumentException for valid fromKey inclusive=true");
        }
    }
}

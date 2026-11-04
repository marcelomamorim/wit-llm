/*
 * @test
 * @summary Test java.util.TreeMap.tailMap(K fromKey, boolean inclusive) method
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.NavigableMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testTailMapWithinRangeInclusiveTrue();
        testTailMapWithinRangeInclusiveFalse();
        testTailMapFromKeyOutOfRange();
    }

    private static void testTailMapWithinRangeInclusiveTrue() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        NavigableMap<Integer, String> tail = map.tailMap(3, true);

        // Should contain keys 3 and 5
        if (!tail.containsKey(3) || !tail.containsKey(5)) {
            throw new RuntimeException("tailMap inclusive=true missing expected keys");
        }
        if (tail.containsKey(1)) {
            throw new RuntimeException("tailMap inclusive=true contains unexpected key 1");
        }
    }

    private static void testTailMapWithinRangeInclusiveFalse() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        NavigableMap<Integer, String> tail = map.tailMap(3, false);

        // Should contain only key 5
        if (!tail.containsKey(5)) {
            throw new RuntimeException("tailMap inclusive=false missing expected key 5");
        }
        if (tail.containsKey(3) || tail.containsKey(1)) {
            throw new RuntimeException("tailMap inclusive=false contains unexpected keys");
        }
    }

    private static void testTailMapFromKeyOutOfRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        try {
            // fromKey less than lowest key (1) -1
            map.tailMap(0, true);
            throw new RuntimeException("Expected IllegalArgumentException for fromKey out of range");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            // fromKey greater than highest key (5) +1
            map.tailMap(6, false);
            throw new RuntimeException("Expected IllegalArgumentException for fromKey out of range");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

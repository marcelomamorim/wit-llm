/*
 * @test
 * @summary Test java.util.TreeMap.headMap(K toKey, boolean inclusive) for range checks and exception
 * @run main TreeMapWitupTest
 */

import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testHeadMapInclusiveTrue();
        testHeadMapInclusiveFalseInRange();
        testHeadMapInclusiveFalseOutOfRange();
        System.out.println("All tests passed.");
    }

    private static void testHeadMapInclusiveTrue() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        // inclusive = true, toKey in range
        NavigableMap<Integer, String> headMap = map.headMap(3, true);
        // Should contain keys <= 3
        if (!headMap.containsKey(1) || !headMap.containsKey(3) || headMap.containsKey(5)) {
            throw new AssertionError("headMap with inclusive=true does not contain expected keys");
        }
    }

    private static void testHeadMapInclusiveFalseInRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        // inclusive = false, toKey in range
        NavigableMap<Integer, String> headMap = map.headMap(3, false);
        // Should contain keys < 3
        if (!headMap.containsKey(1) || headMap.containsKey(3) || headMap.containsKey(5)) {
            throw new AssertionError("headMap with inclusive=false does not contain expected keys");
        }
    }

    private static void testHeadMapInclusiveFalseOutOfRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        // toKey less than lowest key to simulate out of range
        // According to source, this should throw IllegalArgumentException
        boolean thrown = false;
        try {
            map.headMap(0, false);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for toKey out of range");
        }
    }
}

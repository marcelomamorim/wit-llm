/*
 * @test
 * @summary Test TreeMap.tailMap(K fromKey, boolean inclusive) behavior
 * @run main TreeMapWitupTest
 */

import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testTailMapWithinRange();
        testTailMapFromKeyOutOfRange();
    }

    static void testTailMapWithinRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        // tailMap from 3 inclusive
        NavigableMap<Integer, String> tail = map.tailMap(3, true);
        if (!tail.containsKey(3) || !tail.containsKey(5) || tail.containsKey(1)) {
            throw new AssertionError("tailMap inclusive fromKey failed");
        }

        // tailMap from 3 exclusive
        tail = map.tailMap(3, false);
        if (tail.containsKey(3) || !tail.containsKey(5) || tail.containsKey(1)) {
            throw new AssertionError("tailMap exclusive fromKey failed");
        }
    }

    static void testTailMapFromKeyOutOfRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(2, "two");
        map.put(4, "four");

        // The fromKey is less than the lowest key in the map, which is 2
        // According to the method, if fromKey is out of range, it throws IllegalArgumentException
        // We test that exception is thrown
        try {
            map.tailMap(1, true);
            throw new AssertionError("Expected IllegalArgumentException for fromKey out of range");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // The fromKey is greater than the highest key in the map, which is 4
        try {
            map.tailMap(5, true);
            throw new AssertionError("Expected IllegalArgumentException for fromKey out of range");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}

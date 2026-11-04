/*
 * @test
 * @summary Test TreeMap.headMap(K toKey, boolean inclusive) method behavior
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.NavigableMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testHeadMapWithinRange();
        testHeadMapInclusive();
        testHeadMapToKeyOutOfRange();
    }

    private static void testHeadMapWithinRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        NavigableMap<Integer, String> headMap = map.headMap(4, false);

        // headMap should contain keys less than 4
        if (!headMap.containsKey(1) || !headMap.containsKey(3)) {
            throw new RuntimeException("headMap missing expected keys");
        }
        if (headMap.containsKey(5)) {
            throw new RuntimeException("headMap contains key outside range");
        }
    }

    private static void testHeadMapInclusive() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(2, "two");
        map.put(4, "four");
        map.put(6, "six");

        NavigableMap<Integer, String> headMapInclusive = map.headMap(4, true);

        // headMapInclusive should contain key 4
        if (!headMapInclusive.containsKey(4)) {
            throw new RuntimeException("headMap inclusive missing toKey");
        }
        // Should not contain key 6
        if (headMapInclusive.containsKey(6)) {
            throw new RuntimeException("headMap inclusive contains key outside range");
        }
    }

    private static void testHeadMapToKeyOutOfRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");

        try {
            // toKey less than lowest key in map, should throw IllegalArgumentException
            map.headMap(5, true);
            throw new RuntimeException("Expected IllegalArgumentException for toKey out of range");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        try {
            // toKey greater than highest key in map, but inclusive false is allowed
            // Actually, this is allowed if inRange returns true, so no exception expected
            // So test with a toKey definitely out of range (less than lo or greater than hi)
            // But since we do not have access to inRange details, test a key definitely out of range
            // For safety, test with null toKey (should throw NullPointerException or IllegalArgumentException)
            map.headMap(null, true);
            throw new RuntimeException("Expected NullPointerException or IllegalArgumentException for null toKey");
        } catch (NullPointerException | IllegalArgumentException e) {
            // expected
        }
    }
}

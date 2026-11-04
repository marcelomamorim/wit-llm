/*
 * @test
 * @summary Test TreeMap.headMap(K toKey, boolean inclusive) behavior and exceptions
 * @run main TreeMapWitupTest
 */

import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testHeadMapNormal();
        testHeadMapToKeyOutOfRange();
    }

    static void testHeadMapNormal() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        // headMap with inclusive = false
        NavigableMap<Integer, String> headMapExclusive = map.headMap(3, false);
        if (headMapExclusive.containsKey(3)) {
            throw new RuntimeException("headMap with inclusive=false should not contain toKey");
        }
        if (!headMapExclusive.containsKey(1)) {
            throw new RuntimeException("headMap should contain keys less than toKey");
        }

        // headMap with inclusive = true
        NavigableMap<Integer, String> headMapInclusive = map.headMap(3, true);
        if (!headMapInclusive.containsKey(3)) {
            throw new RuntimeException("headMap with inclusive=true should contain toKey");
        }
        if (!headMapInclusive.containsKey(1)) {
            throw new RuntimeException("headMap should contain keys less than toKey");
        }

        // headMap toKey less than lowest key (empty map expected)
        NavigableMap<Integer, String> emptyHeadMap = map.headMap(0, true);
        if (!emptyHeadMap.isEmpty()) {
            throw new RuntimeException("headMap toKey less than lowest key should be empty");
        }
    }

    static void testHeadMapToKeyOutOfRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");

        // toKey out of range: less than low endpoint
        try {
            map.headMap(5, true);
            throw new RuntimeException("Expected IllegalArgumentException for toKey out of range (too low)");
        } catch (IllegalArgumentException expected) {
        }

        // toKey out of range: greater than high endpoint
        try {
            map.headMap(30, true);
            throw new RuntimeException("Expected IllegalArgumentException for toKey out of range (too high)");
        } catch (IllegalArgumentException expected) {
        }
    }
}

/*
 * @test
 * @summary Test tailMap method of TreeMap for various fromKey and inclusive values
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.NavigableMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testTailMapWithValidKeys();
        testTailMapWithInvalidRange();
        testTailMapWithEmptyMap();
    }

    static void testTailMapWithValidKeys() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        NavigableMap<Integer, String> tailInclusive = map.tailMap(3, true);
        assert tailInclusive.size() == 2 : "Expected size 2, got " + tailInclusive.size();
        assert tailInclusive.firstKey() == 3 : "First key should be 3";
        assert tailInclusive.lastKey() == 5 : "Last key should be 5";

        NavigableMap<Integer, String> tailExclusive = map.tailMap(3, false);
        assert tailExclusive.size() == 2 : "Expected size 2, got " + tailExclusive.size();
        assert tailExclusive.firstKey() == 5 : "First key should be 5";
        assert tailExclusive.lastKey() == 5 : "Last key should be 5";
    }

    static void testTailMapWithInvalidRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        boolean exceptionThrown = false;
        try {
            map.tailMap(null, true);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException for null fromKey";
    }

    static void testTailMapWithEmptyMap() {
        TreeMap<Integer, String> emptyMap = new TreeMap<>();
        NavigableMap<Integer, String> tailMap = emptyMap.tailMap(10, true);
        assert tailMap.isEmpty() : "Tail map of empty map should be empty";
        assert tailMap.size() == 0 : "Tail map size should be 0";
    }
}

/* @test    @summary Test TreeMap.headMap(K toKey, boolean inclusive) for valid and invalid inputs    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.NavigableMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testHeadMapValidInclusive();
        testHeadMapValidExclusive();
        testHeadMapInvalidRange();
    }

    static void testHeadMapValidInclusive() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");
        NavigableMap<Integer, String> subMap = map.headMap(4, true);
        assert subMap.size() == 2 : "Expected size 2, got " + subMap.size();
        assert subMap.firstKey() == 1 : "First key should be 1";
        assert subMap.lastKey() == 3 : "Last key should be 3";
    }

    static void testHeadMapValidExclusive() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(2, "two");
        map.put(4, "four");
        map.put(6, "six");
        NavigableMap<Integer, String> subMap = map.headMap(5, false);
        assert subMap.size() == 2 : "Expected size 2, got " + subMap.size();
        assert subMap.firstKey() == 2 : "First key should be 2";
        assert subMap.lastKey() == 4 : "Last key should be 4";
    }

    static void testHeadMapInvalidRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        try {
            map.headMap(null, true);
            assert false : "Expected NullPointerException for null toKey";
        } catch (NullPointerException e) {
            // expected
        }
        try {
            map.headMap(2, true); // valid
        } catch (Exception e) {
            assert false : "Did not expect exception for valid toKey";
        }
    }
}

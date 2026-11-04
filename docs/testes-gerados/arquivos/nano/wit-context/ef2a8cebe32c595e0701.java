/*
 * @test
 * @summary Test for TreeMap.headMap(K toKey, boolean inclusive)
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.NavigableMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testHeadMapWithValidInput();
        testHeadMapWithInvalidRange();
        testHeadMapWithEqualToKey();
        testHeadMapWithInclusiveFalse();
    }

    static void testHeadMapWithValidInput() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        NavigableMap<Integer, String> subMap = map.headMap(3, true);
        assert subMap.size() == 2 : "Expected size 2, got " + subMap.size();
        assert subMap.containsKey(1) : "Missing key 1";
        assert subMap.containsKey(2) : "Missing key 2";
    }

    static void testHeadMapWithInvalidRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            map.headMap(null, true);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testHeadMapWithEqualToKey() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        NavigableMap<Integer, String> subMap = map.headMap(2, false);
        assert subMap.size() == 1 : "Expected size 1, got " + subMap.size();
        assert subMap.containsKey(1) : "Missing key 1";
        assert !subMap.containsKey(2) : "Should not contain key 2";
    }

    static void testHeadMapWithInclusiveFalse() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        NavigableMap<Integer, String> subMap = map.headMap(2, false);
        assert subMap.size() == 1 : "Expected size 1, got " + subMap.size();
        assert subMap.containsKey(1) : "Missing key 1";
        assert !subMap.containsKey(2) : "Should not contain key 2";
    }
}

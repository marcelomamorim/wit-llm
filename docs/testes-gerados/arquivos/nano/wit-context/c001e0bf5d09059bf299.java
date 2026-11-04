/*
 * @test
 * @summary Tests for TreeMap.headMap(K toKey, boolean inclusive)
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.NavigableMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testHeadMapWithValidKeys();
        testHeadMapWithInvalidRange();
    }

    private static void testHeadMapWithValidKeys() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(5, "five");
        map.put(10, "ten");

        NavigableMap<Integer, String> subMap = map.headMap(6, true);
        assert subMap.size() == 2 : "Expected size 2, got " + subMap.size();
        assert subMap.containsKey(1) : "Missing key 1";
        assert subMap.containsKey(5) : "Missing key 5";
        assert !subMap.containsKey(10) : "Should not contain key 10";
    }

    private static void testHeadMapWithInvalidRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(5, "five");
        map.put(10, "ten");

        try {
            // Triggering IllegalArgumentException by passing a toKey out of range
            map.headMap(0, false);
            assert false : "Expected IllegalArgumentException for toKey out of range";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}

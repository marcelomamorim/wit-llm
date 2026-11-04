/* @test    @summary Test TreeMap.headMap method for valid and invalid inputs    @run main TreeMapWitupTest */

import java.util.*;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testHeadMapWithValidKey();
        testHeadMapWithInvalidRange();
    }

    static void testHeadMapWithValidKey() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        try {
            NavigableMap<Integer, String> subMap = map.headMap(3, true);
            if (subMap.size() != 2) {
                throw new AssertionError("Expected size 2, got " + subMap.size());
            }
            if (!subMap.containsKey(1) || !subMap.containsKey(2)) {
                throw new AssertionError("SubMap missing expected keys");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testHeadMapWithInvalidRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            // Using a toKey out of range (less than min key) should throw IllegalArgumentException
            map.headMap(-1, false);
            throw new AssertionError("Expected IllegalArgumentException for out of range toKey");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}

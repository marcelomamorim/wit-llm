/*
 * @test
 * @summary Test for TreeMap.tailMap method, including exception path for out-of-range fromKey.
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.NavigableMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testTailMapWithValidKey();
        testTailMapWithOutOfRangeKey();
    }

    static void testTailMapWithValidKey() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        try {
            NavigableMap<Integer, String> subMap = map.tailMap(2, true);
            if (subMap.size() != 2 || !subMap.containsKey(2) || !subMap.containsKey(3)) {
                throw new AssertionError("Unexpected tailMap content for valid key");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid key", e);
        }
    }

    static void testTailMapWithOutOfRangeKey() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        try {
            map.tailMap(5, true);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}

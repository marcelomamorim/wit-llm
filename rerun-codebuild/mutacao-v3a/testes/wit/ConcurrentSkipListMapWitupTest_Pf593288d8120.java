/*
 * @test
 * @summary Test tailMap method of ConcurrentSkipListMap for null argument
 * @run main ConcurrentSkipListMapWitupTest_Pf593288d8120
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableMap;

public class ConcurrentSkipListMapWitupTest_Pf593288d8120 {

    public static void main(String[] args) {
        testTailMapWithNull();
        testTailMapWithValid();
    }

    static void testTailMapWithNull() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        // Expect NullPointerException when passing null
        try {
            map.tailMap(null);
            // Fail if no exception thrown
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // Expected exception caught
        }
    }

    static void testTailMapWithValid() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        NavigableMap<String, Integer> tail = map.tailMap("b");
        if (tail.size() != 2) {
            throw new AssertionError("tailMap from 'b' should have size 2");
        }
        if (!tail.firstKey().equals("b")) {
            throw new AssertionError("First key of tailMap should be 'b'");
        }
        if (!tail.containsKey("c")) {
            throw new AssertionError("tailMap should contain 'c'");
        }
    }
}

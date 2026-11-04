/*
 * @test
 * @summary Test ConcurrentSkipListMap.lowerEntry(K) behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // lowerEntry on empty map returns null
        if (map.lowerEntry(10) != null) {
            throw new AssertionError("Expected null from lowerEntry on empty map");
        }

        // Add some entries
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        // lowerEntry for key 25 should return entry with key 20
        Map.Entry<Integer, String> e = map.lowerEntry(25);
        if (e == null || !e.getKey().equals(20) || !e.getValue().equals("twenty")) {
            throw new AssertionError("Expected lowerEntry(25) to be (20, twenty), got " + e);
        }

        // lowerEntry for key 10 should return null (no key less than 10)
        if (map.lowerEntry(10) != null) {
            throw new AssertionError("Expected null from lowerEntry(10) when 10 is lowest key");
        }

        // lowerEntry for key less than all keys returns null
        if (map.lowerEntry(5) != null) {
            throw new AssertionError("Expected null from lowerEntry(5) when 5 < all keys");
        }

        // lowerEntry for key greater than all keys returns highest key
        e = map.lowerEntry(40);
        if (e == null || !e.getKey().equals(30) || !e.getValue().equals("thirty")) {
            throw new AssertionError("Expected lowerEntry(40) to be (30, thirty), got " + e);
        }

        System.out.println("All tests passed.");
    }
}

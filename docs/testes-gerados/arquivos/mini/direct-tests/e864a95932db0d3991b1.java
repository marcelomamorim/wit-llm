/*
 * @test
 * @summary Test ConcurrentSkipListMap.higherEntry(K) basic behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Test higherEntry on empty map returns null
        if (map.higherEntry(10) != null) {
            throw new AssertionError("Expected null from higherEntry on empty map");
        }

        // Insert some entries
        map.put(5, "five");
        map.put(10, "ten");
        map.put(15, "fifteen");

        // Test higherEntry for key less than lowest key
        Map.Entry<Integer, String> e = map.higherEntry(1);
        if (e == null || !e.getKey().equals(5) || !e.getValue().equals("five")) {
            throw new AssertionError("Expected higherEntry(1) to be (5,five), got " + e);
        }

        // Test higherEntry for key equal to existing key
        e = map.higherEntry(10);
        if (e == null || !e.getKey().equals(15) || !e.getValue().equals("fifteen")) {
            throw new AssertionError("Expected higherEntry(10) to be (15,fifteen), got " + e);
        }

        // Test higherEntry for key between existing keys
        e = map.higherEntry(7);
        if (e == null || !e.getKey().equals(10) || !e.getValue().equals("ten")) {
            throw new AssertionError("Expected higherEntry(7) to be (10,ten), got " + e);
        }

        // Test higherEntry for key greater than highest key returns null
        if (map.higherEntry(20) != null) {
            throw new AssertionError("Expected null from higherEntry(20) as no higher key exists");
        }

        System.out.println("All tests passed.");
    }
}

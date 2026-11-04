/*
 * @test
 * @summary Test ConcurrentSkipListMap headMap(K toKey) method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableMap;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        // Test headMap with toKey present in map
        NavigableMap<Integer, String> headMap1 = map.headMap(3);
        if (!headMap1.containsKey(1) || headMap1.containsKey(3)) {
            throw new AssertionError("headMap(3) should include keys less than 3 and exclude 3");
        }

        // Test headMap with toKey not present in map
        NavigableMap<Integer, String> headMap2 = map.headMap(4);
        if (!headMap2.containsKey(1) || !headMap2.containsKey(3) || headMap2.containsKey(5)) {
            throw new AssertionError("headMap(4) should include keys less than 4 and exclude 5");
        }

        // Test headMap with toKey less than any key in map
        NavigableMap<Integer, String> headMap3 = map.headMap(0);
        if (!headMap3.isEmpty()) {
            throw new AssertionError("headMap(0) should be empty");
        }

        // Test headMap with toKey greater than all keys in map
        NavigableMap<Integer, String> headMap4 = map.headMap(10);
        if (!headMap4.containsKey(1) || !headMap4.containsKey(3) || !headMap4.containsKey(5)) {
            throw new AssertionError("headMap(10) should include all keys");
        }

        System.out.println("All tests passed.");
    }
}

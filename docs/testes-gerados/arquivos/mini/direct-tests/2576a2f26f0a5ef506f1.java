/*
 * @test
 * @summary Test ConcurrentSkipListMap.subMap(K fromKey, K toKey) basic behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        // subMap from 1 (inclusive) to 5 (exclusive) should contain keys 1 and 3
        NavigableMap<Integer, String> sub = map.subMap(1, 5);
        if (!sub.containsKey(1)) throw new AssertionError("subMap missing key 1");
        if (!sub.containsKey(3)) throw new AssertionError("subMap missing key 3");
        if (sub.containsKey(5)) throw new AssertionError("subMap should not contain key 5");

        // subMap from 2 to 5 should contain only key 3
        NavigableMap<Integer, String> sub2 = map.subMap(2, 5);
        if (sub2.containsKey(1)) throw new AssertionError("subMap2 should not contain key 1");
        if (!sub2.containsKey(3)) throw new AssertionError("subMap2 missing key 3");
        if (sub2.containsKey(5)) throw new AssertionError("subMap2 should not contain key 5");

        // subMap from 3 to 3 is empty (fromKey inclusive, toKey exclusive)
        NavigableMap<Integer, String> sub3 = map.subMap(3, 3);
        if (!sub3.isEmpty()) throw new AssertionError("subMap3 should be empty");

        // subMap with fromKey > toKey should throw IllegalArgumentException
        boolean threw = false;
        try {
            map.subMap(5, 1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) throw new AssertionError("subMap with fromKey > toKey should throw IllegalArgumentException");
    }
}

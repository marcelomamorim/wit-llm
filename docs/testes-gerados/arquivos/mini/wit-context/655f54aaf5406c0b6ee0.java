/*
 * @test
 * @summary Test ConcurrentSkipListMap.headMap(K) for null argument and normal usage
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListMap.SubMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        // Test normal usage: headMap with a valid key
        SubMap<Integer, String> headMap = map.headMap(3);
        if (!headMap.containsKey(1) || !headMap.containsKey(2) || headMap.containsKey(3)) {
            throw new AssertionError("headMap does not contain expected keys");
        }

        // Test null argument triggers NullPointerException
        try {
            map.headMap(null);
            throw new AssertionError("Expected NullPointerException for null toKey");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Test ConcurrentSkipListMap.headMap(K toKey, boolean inclusive) for null argument and normal behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");

        // Test that headMap throws NullPointerException if toKey is null
        boolean npeThrown = false;
        try {
            map.headMap(null, true);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("headMap(null, true) did not throw NullPointerException");
        }

        // Test normal behavior: headMap with inclusive = true
        ConcurrentSkipListMap.SubMap<String, String> headMapInclusive = map.headMap("b", true);
        if (!headMapInclusive.containsKey("a") || !headMapInclusive.containsKey("b") || headMapInclusive.containsKey("c")) {
            throw new AssertionError("headMap inclusive did not contain expected keys");
        }

        // Test normal behavior: headMap with inclusive = false
        ConcurrentSkipListMap.SubMap<String, String> headMapExclusive = map.headMap("b", false);
        if (!headMapExclusive.containsKey("a") || headMapExclusive.containsKey("b") || headMapExclusive.containsKey("c")) {
            throw new AssertionError("headMap exclusive did not contain expected keys");
        }
    }
}

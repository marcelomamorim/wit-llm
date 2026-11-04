/*
 * @test
 * @summary Test ConcurrentSkipListMap.headMap(K toKey, boolean inclusive) method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testHeadMapNullKey();
        testHeadMapInclusiveBehavior();
    }

    static void testHeadMapNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        boolean npeThrown = false;
        try {
            map.headMap(null, true);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("headMap(null, true) did not throw NullPointerException");
        }
    }

    static void testHeadMapInclusiveBehavior() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        // inclusive = true: toKey included
        NavigableMap<Integer, String> headMapInclusive = map.headMap(2, true);
        if (!headMapInclusive.containsKey(2)) {
            throw new AssertionError("headMap with inclusive=true should contain toKey");
        }
        if (!headMapInclusive.containsKey(1)) {
            throw new AssertionError("headMap should contain keys less than toKey");
        }
        if (headMapInclusive.containsKey(3)) {
            throw new AssertionError("headMap should not contain keys greater than toKey");
        }

        // inclusive = false: toKey excluded
        NavigableMap<Integer, String> headMapExclusive = map.headMap(2, false);
        if (headMapExclusive.containsKey(2)) {
            throw new AssertionError("headMap with inclusive=false should not contain toKey");
        }
        if (!headMapExclusive.containsKey(1)) {
            throw new AssertionError("headMap should contain keys less than toKey");
        }
        if (headMapExclusive.containsKey(3)) {
            throw new AssertionError("headMap should not contain keys greater than toKey");
        }
    }
}

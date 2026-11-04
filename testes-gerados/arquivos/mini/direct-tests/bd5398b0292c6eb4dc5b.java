/*
 * @test
 * @summary Test ConcurrentSkipListMap.tailMap(K fromKey, boolean inclusive) method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testTailMapWithValidKey();
        testTailMapWithNullKey();
    }

    private static void testTailMapWithValidKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        NavigableMap<Integer, String> tailMapInclusive = map.tailMap(3, true);
        if (!tailMapInclusive.containsKey(3) || !tailMapInclusive.containsKey(5)) {
            throw new AssertionError("tailMap inclusive should contain keys 3 and 5");
        }

        NavigableMap<Integer, String> tailMapExclusive = map.tailMap(3, false);
        if (tailMapExclusive.containsKey(3) || !tailMapExclusive.containsKey(5)) {
            throw new AssertionError("tailMap exclusive should not contain key 3 but contain key 5");
        }
    }

    private static void testTailMapWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.tailMap(null, true);
            throw new AssertionError("Expected NullPointerException for null fromKey");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Test ConcurrentSkipListMap.tailMap(K fromKey, boolean inclusive) for null argument and normal behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testTailMapNullKey();
        testTailMapNormal();
    }

    private static void testTailMapNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean npeThrown = false;
        try {
            map.tailMap(null, true);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when fromKey is null");
        }
    }

    private static void testTailMapNormal() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");

        NavigableMap<String, String> tailInclusive = map.tailMap("b", true);
        if (!tailInclusive.containsKey("b") || !tailInclusive.containsKey("c") || tailInclusive.containsKey("a")) {
            throw new AssertionError("tailMap inclusive=true did not return expected keys");
        }

        NavigableMap<String, String> tailExclusive = map.tailMap("b", false);
        if (tailExclusive.containsKey("b") || !tailExclusive.containsKey("c") || tailExclusive.containsKey("a")) {
            throw new AssertionError("tailMap inclusive=false did not return expected keys");
        }
    }
}

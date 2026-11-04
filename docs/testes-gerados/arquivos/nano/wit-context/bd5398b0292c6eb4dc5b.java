/*
 * @test
 * @summary Test tailMap method for ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testTailMapWithNullKey();
        testTailMapWithValidKey();
    }

    private static void testTailMapWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        boolean exceptionThrown = false;
        try {
            map.tailMap(null, true);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null as fromKey");
        }
    }

    private static void testTailMapWithValidKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        ConcurrentSkipListMap<Integer, String>.SubMap subMap = map.tailMap(2, true);
        boolean containsKey2 = subMap.containsKey(2);
        boolean containsKey3 = subMap.containsKey(3);
        if (!containsKey2 || !containsKey3) {
            throw new AssertionError("SubMap should contain keys 2 and 3");
        }
        if (subMap.size() != 2) {
            throw new AssertionError("SubMap size should be 2");
        }
    }
}

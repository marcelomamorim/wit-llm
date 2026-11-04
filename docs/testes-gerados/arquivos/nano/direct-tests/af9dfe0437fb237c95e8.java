/*
 * @test
 * @summary Test descendingMap() method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testDescendingMapReturnsNonNull();
        testDescendingMapConsistency();
    }

    private static void testDescendingMapReturnsNonNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        Object result = map.descendingMap();
        if (result == null) {
            throw new AssertionError("descendingMap() returned null");
        }
    }

    private static void testDescendingMapConsistency() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        Object descendingMap = map.descendingMap();
        if (!(descendingMap instanceof java.util.NavigableMap)) {
            throw new AssertionError("descendingMap() does not return a NavigableMap");
        }
        // Verify that the descending map contains the same entries in reverse order
        java.util.NavigableMap<Integer, String> navMap = (java.util.NavigableMap<Integer, String>) descendingMap;
        List<Integer> keys = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : navMap.entrySet()) {
            keys.add(entry.getKey());
        }
        if (!keys.equals(java.util.Arrays.asList(3, 2, 1))) {
            throw new AssertionError("Descending map entries are not in reverse order");
        }
    }
}

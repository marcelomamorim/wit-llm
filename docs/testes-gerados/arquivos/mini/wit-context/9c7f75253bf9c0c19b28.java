/*
 * @test
 * @summary Test ConcurrentSkipListMap.clear() for basic correctness and regression
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Comparator;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testClearEmptyMap();
        testClearNonEmptyMap();
        testClearWithNullKey();
    }

    static void testClearEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.clear();
        if (!map.isEmpty()) {
            throw new AssertionError("Map should be empty after clear on empty map");
        }
    }

    static void testClearNonEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.clear();
        if (!map.isEmpty()) {
            throw new AssertionError("Map should be empty after clear on non-empty map");
        }
        if (map.containsKey(1) || map.containsKey(2) || map.containsKey(3)) {
            throw new AssertionError("Map should not contain keys after clear");
        }
    }

    static void testClearWithNullKey() {
        // ConcurrentSkipListMap does not allow null keys, so put(null, ...) throws NPE
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        boolean npeThrown = false;
        try {
            map.put(null, "null");
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when inserting null key");
        }
        // Clear should not throw and map should remain empty
        map.clear();
        if (!map.isEmpty()) {
            throw new AssertionError("Map should be empty after clear");
        }
    }
}

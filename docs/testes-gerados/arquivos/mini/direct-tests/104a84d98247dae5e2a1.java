/*
 * @test
 * @summary Test ConcurrentSkipListMap.lastKey() behavior for ascending and descending maps
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testLastKeyAscending();
        testLastKeyDescending();
        testLastKeyEmptyMap();
    }

    static void testLastKeyAscending() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(2, "two");

        Integer lastKey = map.lastKey();
        if (!lastKey.equals(3)) {
            throw new AssertionError("Expected lastKey to be 3 but was " + lastKey);
        }
    }

    static void testLastKeyDescending() {
        // Create descending map view
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(15, "fifteen");

        // descendingMap returns a NavigableMap view with reversed order
        java.util.NavigableMap<Integer, String> descMap = map.descendingMap();

        Integer lastKey = descMap.lastKey();
        // For descending map, lastKey() returns lowestKey(), which should be 10
        if (!lastKey.equals(10)) {
            throw new AssertionError("Expected lastKey of descending map to be 10 but was " + lastKey);
        }
    }

    static void testLastKeyEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.lastKey();
            throw new AssertionError("Expected NoSuchElementException for lastKey() on empty map");
        } catch (NoSuchElementException expected) {
            // expected
        }

        java.util.NavigableMap<Integer, String> descMap = map.descendingMap();
        try {
            descMap.lastKey();
            throw new AssertionError("Expected NoSuchElementException for lastKey() on empty descending map");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}

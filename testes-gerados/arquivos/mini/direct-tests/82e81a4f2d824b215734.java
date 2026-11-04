/*
 * @test
 * @summary Test ConcurrentSkipListMap.firstKey() behavior for ascending and descending maps
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testFirstKeyAscending();
        testFirstKeyDescending();
        testFirstKeyEmptyMap();
    }

    static void testFirstKeyAscending() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(5, "five");
        map.put(20, "twenty");

        Integer firstKey = map.firstKey();
        if (!firstKey.equals(5)) {
            throw new AssertionError("Expected firstKey to be 5 but was " + firstKey);
        }
    }

    static void testFirstKeyDescending() {
        // Create descending map view
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(5, "five");
        map.put(20, "twenty");

        ConcurrentSkipListMap<Integer, String> descendingMap = (ConcurrentSkipListMap<Integer, String>) map.descendingMap();

        Integer firstKey = descendingMap.firstKey();
        if (!firstKey.equals(20)) {
            throw new AssertionError("Expected firstKey of descending map to be 20 but was " + firstKey);
        }
    }

    static void testFirstKeyEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.firstKey();
            throw new AssertionError("Expected NoSuchElementException for firstKey on empty map");
        } catch (NoSuchElementException expected) {
            // expected
        }

        ConcurrentSkipListMap<Integer, String> descendingMap = (ConcurrentSkipListMap<Integer, String>) map.descendingMap();
        try {
            descendingMap.firstKey();
            throw new AssertionError("Expected NoSuchElementException for firstKey on empty descending map");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Checks firstKey on ConcurrentSkipListMap submap views, including descending views and empty ranges
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ConcurrentSkipListMap<Integer, String> sampleMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 5; i++) {
            map.put(i, "v" + i);
        }
        return map;
    }

    static void testDescendingViewFirstKeyIsHighest() {
        ConcurrentSkipListMap<Integer, String> map = sampleMap();
        NavigableMap<Integer, String> desc = map.descendingMap();
        check(desc.firstKey().equals(5), "descending firstKey should be 5, got " + desc.firstKey());
    }

    static void testAscendingSubMapFirstKey() {
        ConcurrentSkipListMap<Integer, String> map = sampleMap();
        NavigableMap<Integer, String> sub = map.subMap(2, true, 4, true);
        check(sub.firstKey().equals(2), "ascending submap firstKey should be 2, got " + sub.firstKey());
    }

    static void testDescendingSubMapFirstKey() {
        ConcurrentSkipListMap<Integer, String> map = sampleMap();
        NavigableMap<Integer, String> sub = map.subMap(2, true, 4, true).descendingMap();
        check(sub.firstKey().equals(4), "descending submap firstKey should be 4, got " + sub.firstKey());
    }

    static void testDescendingSubMapExclusiveBounds() {
        ConcurrentSkipListMap<Integer, String> map = sampleMap();
        NavigableMap<Integer, String> sub = map.subMap(2, false, 4, false).descendingMap();
        check(sub.firstKey().equals(3), "exclusive descending submap firstKey should be 3, got " + sub.firstKey());
    }

    static void testEmptyRangeThrows() {
        ConcurrentSkipListMap<Integer, String> map = sampleMap();
        NavigableMap<Integer, String> sub = map.subMap(10, true, 20, true);
        boolean thrown = false;
        try {
            sub.firstKey();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "firstKey on empty ascending range should throw NoSuchElementException");
    }

    static void testEmptyDescendingRangeThrows() {
        ConcurrentSkipListMap<Integer, String> map = sampleMap();
        NavigableMap<Integer, String> sub = map.subMap(10, true, 20, true).descendingMap();
        boolean thrown = false;
        try {
            sub.firstKey();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "firstKey on empty descending range should throw NoSuchElementException");
    }

    static void testDescendingViewReflectsUpdate() {
        ConcurrentSkipListMap<Integer, String> map = sampleMap();
        NavigableMap<Integer, String> desc = map.descendingMap();
        map.put(9, "v9");
        check(desc.firstKey().equals(9), "descending firstKey should reflect new maximum 9, got " + desc.firstKey());
    }

    public static void main(String[] args) {
        testDescendingViewFirstKeyIsHighest();
        testAscendingSubMapFirstKey();
        testDescendingSubMapFirstKey();
        testDescendingSubMapExclusiveBounds();
        testEmptyRangeThrows();
        testEmptyDescendingRangeThrows();
        testDescendingViewReflectsUpdate();
    }
}

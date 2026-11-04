/*
 * @test
 * @summary Functional checks for ConcurrentSkipListMap.floorKey on empty, natural-order, reverse-order and modified maps
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testEmptyMap();
        testNaturalOrderFloor();
        testExactMatchReturnsSameKey();
        testReverseComparatorFloor();
        testFloorAfterRemoval();
        testConstructedFromMap();
    }

    static void testEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        assertEquals(null, map.floorKey(1));
        assertEquals(null, map.floorKey(Integer.MIN_VALUE));
    }

    static void testNaturalOrderFloor() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(20, "b");
        map.put(30, "c");
        assertEquals(null, map.floorKey(5));
        assertEquals(Integer.valueOf(10), map.floorKey(15));
        assertEquals(Integer.valueOf(20), map.floorKey(25));
        assertEquals(Integer.valueOf(30), map.floorKey(35));
    }

    static void testExactMatchReturnsSameKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(20, "b");
        assertEquals(Integer.valueOf(10), map.floorKey(10));
        assertEquals(Integer.valueOf(20), map.floorKey(20));
    }

    static void testReverseComparatorFloor() {
        ConcurrentSkipListMap<Integer, String> map =
                new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        map.put(10, "a");
        map.put(20, "b");
        map.put(30, "c");
        // In reverse order, 20 is the closest key that is not after 15.
        assertEquals(Integer.valueOf(20), map.floorKey(15));
        // In reverse order, no key precedes or equals 35.
        assertEquals(null, map.floorKey(35));
    }

    static void testFloorAfterRemoval() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(20, "b");
        map.put(30, "c");
        map.remove(20);
        assertEquals(Integer.valueOf(10), map.floorKey(25));
    }

    static void testConstructedFromMap() {
        Map<Integer, String> src = new HashMap<>();
        src.put(5, "x");
        src.put(15, "y");
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>(src);
        assertEquals(null, map.floorKey(4));
        assertEquals(Integer.valueOf(5), map.floorKey(14));
        assertEquals(Integer.valueOf(15), map.floorKey(15));
    }

    static void assertEquals(Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}

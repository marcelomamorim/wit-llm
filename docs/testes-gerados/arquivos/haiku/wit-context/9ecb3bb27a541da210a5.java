/*
 * @test
 * @summary Verify ConcurrentSkipListMap.floorKey returns the greatest key not greater than the argument and throws NullPointerException for a null key
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapReturnsNull();
        testNullKeyThrowsNpe();
        testExactMatch();
        testBetweenKeys();
        testBelowMinimumReturnsNull();
        testAboveMaximumReturnsLastKey();
        testReverseComparator();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ConcurrentSkipListMap<Integer, String> sampleMap() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(10, "ten");
        m.put(20, "twenty");
        m.put(30, "thirty");
        return m;
    }

    static void testEmptyMapReturnsNull() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        check(m.floorKey(5) == null, "floorKey on empty map must return null");
    }

    static void testNullKeyThrowsNpe() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        boolean thrown = false;
        try {
            m.floorKey(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "floorKey(null) must throw NullPointerException");
    }

    static void testExactMatch() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        check(m.floorKey(20) == 20, "floorKey of an existing key must return that key");
    }

    static void testBetweenKeys() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        check(m.floorKey(25) == 20, "floorKey(25) must return 20");
        check(m.floorKey(11) == 10, "floorKey(11) must return 10");
    }

    static void testBelowMinimumReturnsNull() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        check(m.floorKey(5) == null, "floorKey below minimum key must return null");
    }

    static void testAboveMaximumReturnsLastKey() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        check(m.floorKey(100) == 30, "floorKey above maximum key must return 30");
    }

    static void testReverseComparator() {
        ConcurrentSkipListMap<Integer, String> m =
                new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        m.put(10, "ten");
        m.put(20, "twenty");
        m.put(30, "thirty");
        check(m.floorKey(25) == 30, "reverse order floorKey(25) must return 30");
        check(m.floorKey(5) == null || m.floorKey(5) == 10,
                "reverse order floorKey must respect comparator");
    }
}

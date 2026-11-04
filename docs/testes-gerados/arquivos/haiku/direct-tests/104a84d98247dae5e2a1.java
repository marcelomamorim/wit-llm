/*
 * @test
 * @summary Checks lastKey() on ConcurrentSkipListMap and its descending and sub-map views using only public API
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new RuntimeException(message + ": expected " + expected + " but was " + actual);
        }
    }

    static ConcurrentSkipListMap<Integer, String> sampleMap() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(3, "c");
        m.put(1, "a");
        m.put(5, "e");
        m.put(2, "b");
        m.put(4, "d");
        return m;
    }

    static void testNaturalOrder() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        checkEquals(5, m.lastKey(), "lastKey natural order");
    }

    static void testSingleElement() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(7, "x");
        checkEquals(7, m.lastKey(), "lastKey single element");
    }

    static void testEmptyThrows() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        try {
            m.lastKey();
            throw new RuntimeException("lastKey on empty map should throw NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testComparatorOrder() {
        ConcurrentSkipListMap<Integer, String> m =
            new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        m.put(1, "a");
        m.put(5, "e");
        m.put(3, "c");
        checkEquals(1, m.lastKey(), "lastKey with reverse comparator");
    }

    static void testDescendingView() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        ConcurrentNavigableMap<Integer, String> d = m.descendingMap();
        checkEquals(1, d.lastKey(), "lastKey on descending view");
    }

    static void testSubMapView() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        ConcurrentNavigableMap<Integer, String> s = m.subMap(1, true, 4, true);
        checkEquals(4, s.lastKey(), "lastKey on closed subMap");
    }

    static void testHeadMapView() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        ConcurrentNavigableMap<Integer, String> h = m.headMap(3);
        checkEquals(2, h.lastKey(), "lastKey on headMap");
    }

    static void testTailMapView() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        ConcurrentNavigableMap<Integer, String> t = m.tailMap(3);
        checkEquals(5, t.lastKey(), "lastKey on tailMap");
    }

    static void testEmptySubMapThrows() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        ConcurrentNavigableMap<Integer, String> s = m.subMap(10, true, 20, true);
        try {
            s.lastKey();
            throw new RuntimeException("lastKey on empty subMap should throw NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testReflectsUpdate() {
        ConcurrentSkipListMap<Integer, String> m = sampleMap();
        checkEquals(5, m.lastKey(), "before update");
        m.put(9, "i");
        checkEquals(9, m.lastKey(), "after put of larger key");
        m.remove(9);
        checkEquals(5, m.lastKey(), "after removal of largest key");
    }

    public static void main(String[] args) {
        testNaturalOrder();
        testSingleElement();
        testEmptyThrows();
        testComparatorOrder();
        testDescendingView();
        testSubMapView();
        testHeadMapView();
        testTailMapView();
        testEmptySubMapThrows();
        testReflectsUpdate();
    }
}

/*
 * @test
 * @summary Witup-derived checks for ConcurrentSkipListMap.ceilingKey behavior and null key handling
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapReturnsNull();
        testCeilingKeyValues();
        testNullKeyThrowsNpeOnEmptyMap();
        testNullKeyThrowsNpeOnNonEmptyMap();
    }

    static void testEmptyMapReturnsNull() {
        ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        if (empty.ceilingKey(1) != null) {
            throw new AssertionError("ceilingKey on empty map should return null");
        }
    }

    static void testCeilingKeyValues() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(3, "c");
        m.put(5, "e");
        assertKey(m.ceilingKey(0), 1, "ceilingKey(0)");
        assertKey(m.ceilingKey(1), 1, "ceilingKey(1)");
        assertKey(m.ceilingKey(2), 3, "ceilingKey(2)");
        assertKey(m.ceilingKey(3), 3, "ceilingKey(3)");
        assertKey(m.ceilingKey(4), 5, "ceilingKey(4)");
        assertKey(m.ceilingKey(5), 5, "ceilingKey(5)");
        if (m.ceilingKey(6) != null) {
            throw new AssertionError("ceilingKey(6) should return null when no greater or equal key exists");
        }
    }

    static void testNullKeyThrowsNpeOnEmptyMap() {
        ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        boolean thrown = false;
        try {
            empty.ceilingKey(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("ceilingKey(null) should throw NullPointerException on empty map");
        }
    }

    static void testNullKeyThrowsNpeOnNonEmptyMap() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        boolean thrown = false;
        try {
            m.ceilingKey(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("ceilingKey(null) should throw NullPointerException on non-empty map");
        }
    }

    static void assertKey(Integer actual, int expected, String label) {
        if (actual == null || actual.intValue() != expected) {
            throw new AssertionError(label + " expected " + expected + " but was " + actual);
        }
    }
}

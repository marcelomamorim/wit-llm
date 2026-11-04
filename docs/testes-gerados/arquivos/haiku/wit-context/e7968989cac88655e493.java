/*
 * @test
 * @summary Witup-derived checks for ConcurrentSkipListMap.ceilingEntry ordering, null results and null key rejection
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testEmptyMap();
        testExactAndNextGreater();
        testNoGreaterKey();
        testBelowAllKeys();
        testReverseComparator();
        testNullKeyThrowsNpe();
    }

    static void testEmptyMap() {
        ConcurrentSkipListMap<Integer,Integer> m = new ConcurrentSkipListMap<>();
        check(m.ceilingEntry(1) == null, "empty map must return null");
    }

    static void testExactAndNextGreater() {
        ConcurrentSkipListMap<Integer,Integer> m = new ConcurrentSkipListMap<>();
        m.put(5, 50);
        m.put(10, 100);
        m.put(20, 200);

        Map.Entry<Integer,Integer> e = m.ceilingEntry(10);
        check(e != null, "exact match must return entry");
        check(e.getKey() == 10, "exact match key mismatch");
        check(e.getValue() == 100, "exact match value mismatch");

        e = m.ceilingEntry(11);
        check(e != null, "next greater must return entry");
        check(e.getKey() == 20, "ceiling of 11 must be 20");
        check(e.getValue() == 200, "ceiling of 11 value mismatch");

        e = m.ceilingEntry(4);
        check(e != null, "key below first must return first entry");
        check(e.getKey() == 5, "ceiling of 4 must be 5");
    }

    static void testNoGreaterKey() {
        ConcurrentSkipListMap<Integer,Integer> m = new ConcurrentSkipListMap<>();
        m.put(5, 50);
        m.put(10, 100);
        check(m.ceilingEntry(11) == null, "no key >= 11 must return null");
    }

    static void testBelowAllKeys() {
        ConcurrentSkipListMap<Integer,Integer> m = new ConcurrentSkipListMap<>();
        m.put(5, 50);
        Map.Entry<Integer,Integer> e = m.ceilingEntry(Integer.MIN_VALUE);
        check(e != null && e.getKey() == 5, "minimum probe must return smallest key");
    }

    static void testReverseComparator() {
        ConcurrentSkipListMap<Integer,Integer> m =
                new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        m.put(5, 50);
        m.put(10, 100);
        m.put(20, 200);

        Map.Entry<Integer,Integer> e = m.ceilingEntry(10);
        check(e != null && e.getKey() == 10, "reverse comparator exact match failed");

        e = m.ceilingEntry(11);
        check(e != null && e.getKey() == 10, "reverse comparator ceiling of 11 must be 10");
    }

    static void testNullKeyThrowsNpe() {
        ConcurrentSkipListMap<Integer,Integer> m = new ConcurrentSkipListMap<>();
        m.put(1, 10);
        boolean thrown = false;
        try {
            m.ceilingEntry(null);
        } catch (NullPointerException ex) {
            thrown = true;
        }
        check(thrown, "null key must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

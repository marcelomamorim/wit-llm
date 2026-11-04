/*
 * @test
 * @summary Basic observable behavior of ConcurrentSkipListMap.ceilingKey
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapReturnsNull();
        testExactMatchReturnsKey();
        testBetweenKeysReturnsNextGreaterKey();
        testBelowMinimumReturnsMinimum();
        testAboveMaximumReturnsNull();
        testReverseComparatorUsesComparatorOrder();
        testRemovedKeyIsNotReturned();
    }

    static void testEmptyMapReturnsNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        check(null, map.ceilingKey(1), "empty map");
    }

    static void testExactMatchReturnsKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(20, "b");
        map.put(30, "c");
        check(20, map.ceilingKey(20), "exact match");
    }

    static void testBetweenKeysReturnsNextGreaterKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(20, "b");
        map.put(30, "c");
        check(20, map.ceilingKey(15), "between keys");
    }

    static void testBelowMinimumReturnsMinimum() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(20, "b");
        check(10, map.ceilingKey(5), "below minimum");
    }

    static void testAboveMaximumReturnsNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(20, "b");
        check(null, map.ceilingKey(21), "above maximum");
    }

    static void testReverseComparatorUsesComparatorOrder() {
        ConcurrentSkipListMap<Integer, String> map =
                new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        map.put(10, "a");
        map.put(20, "b");
        map.put(30, "c");
        // In reverse order the sequence is 30, 20, 10; the least element
        // not before 15 in that ordering is 10.
        check(10, map.ceilingKey(15), "reverse comparator");
    }

    static void testRemovedKeyIsNotReturned() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(20, "b");
        map.put(30, "c");
        map.remove(20);
        check(30, map.ceilingKey(20), "removed key");
    }

    static void check(Object expected, Object actual, String msg) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new RuntimeException(msg + ": expected " + expected + " but was " + actual);
        }
    }
}

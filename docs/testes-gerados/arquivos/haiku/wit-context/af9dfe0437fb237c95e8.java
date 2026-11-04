/*
 * @test
 * @summary Witup-derived black-box checks for ConcurrentSkipListMap.descendingMap()
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableMap;
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

    static void testEmptyMapDescendingView() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        NavigableMap<Integer, String> d = m.descendingMap();
        checkEquals(true, d.isEmpty(), "descending view of empty map must be empty");
        checkEquals(0, d.size(), "descending view of empty map must have size 0");
    }

    static void testDescendingIterationOrder() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "one");
        m.put(2, "two");
        m.put(3, "three");
        NavigableMap<Integer, String> d = m.descendingMap();
        List<Integer> keys = new ArrayList<>(d.keySet());
        checkEquals(Arrays.asList(3, 2, 1), keys, "descending key order");
        checkEquals(3, d.firstKey(), "first key of descending view");
        checkEquals(1, d.lastKey(), "last key of descending view");
    }

    static void testDescendingGetAndWriteThrough() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "one");
        m.put(2, "two");
        NavigableMap<Integer, String> d = m.descendingMap();
        checkEquals("two", d.get(2), "get through descending view");
        d.put(4, "four");
        checkEquals("four", m.get(4), "put through descending view must reach base map");
        checkEquals(4, m.lastKey(), "base map must observe new largest key");
        m.put(0, "zero");
        checkEquals(0, d.lastKey(), "descending view must observe base map updates");
    }

    static void testDoubleDescendingRestoresAscendingOrder() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(5, "five");
        m.put(7, "seven");
        m.put(6, "six");
        NavigableMap<Integer, String> dd = m.descendingMap().descendingMap();
        List<Integer> keys = new ArrayList<>(dd.keySet());
        checkEquals(Arrays.asList(5, 6, 7), keys, "double descending must restore ascending order");
    }

    static void testDescendingOfComparatorOrderedMap() {
        ConcurrentSkipListMap<Integer, String> m =
            new ConcurrentSkipListMap<Integer, String>(Comparator.reverseOrder());
        m.put(1, "one");
        m.put(2, "two");
        m.put(3, "three");
        List<Integer> keysDesc = new ArrayList<>(m.descendingMap().keySet());
        checkEquals(Arrays.asList(1, 2, 3), keysDesc,
                    "descending view of reverse-ordered map must be ascending natural order");
    }

    static void testDescendingViewIsIndependentObjectButConsistent() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(10, "ten");
        NavigableMap<Integer, String> d1 = m.descendingMap();
        NavigableMap<Integer, String> d2 = m.descendingMap();
        checkEquals(new ArrayList<>(d1.keySet()), new ArrayList<>(d2.keySet()),
                    "repeated descendingMap calls must expose the same ordering");
        check(d1 != m, "descendingMap must not return the base map instance");
    }

    public static void main(String[] args) {
        testEmptyMapDescendingView();
        testDescendingIterationOrder();
        testDescendingGetAndWriteThrough();
        testDoubleDescendingRestoresAscendingOrder();
        testDescendingOfComparatorOrderedMap();
        testDescendingViewIsIndependentObjectButConsistent();
    }
}

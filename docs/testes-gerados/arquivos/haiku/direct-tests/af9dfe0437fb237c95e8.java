/*
 * @test
 * @summary Basic observable behavior of ConcurrentSkipListMap.descendingMap()
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testOrderAndEnds();
        testViewWritesThrough();
        testDoubleDescendingIsAscending();
        testEmptyView();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ConcurrentNavigableMap<Integer, String> newMap() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        return m;
    }

    static List<Integer> keys(ConcurrentNavigableMap<Integer, String> map) {
        List<Integer> result = new ArrayList<>();
        for (Integer k : map.keySet()) {
            result.add(k);
        }
        return result;
    }

    static void testOrderAndEnds() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        ConcurrentNavigableMap<Integer, String> d = m.descendingMap();
        check(d.firstKey().equals(3), "descending firstKey should be 3");
        check(d.lastKey().equals(1), "descending lastKey should be 1");
        check(keys(d).equals(Arrays.asList(3, 2, 1)), "descending key order mismatch");
        check("b".equals(d.get(2)), "get through descending view failed");
    }

    static void testViewWritesThrough() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        ConcurrentNavigableMap<Integer, String> d = m.descendingMap();
        d.put(4, "d");
        check(m.containsKey(4), "put via descending view not visible in backing map");
        check(m.lastKey().equals(4), "backing map lastKey should be 4");
        m.put(0, "z");
        check(d.lastKey().equals(0), "descending view should reflect backing map update");
        check(keys(d).equals(Arrays.asList(4, 3, 2, 1, 0)), "descending order after updates mismatch");
    }

    static void testDoubleDescendingIsAscending() {
        ConcurrentNavigableMap<Integer, String> d = newMap().descendingMap();
        ConcurrentNavigableMap<Integer, String> dd = d.descendingMap();
        check(dd.firstKey().equals(1), "double descending firstKey should be 1");
        check(dd.lastKey().equals(3), "double descending lastKey should be 3");
        check(keys(dd).equals(Arrays.asList(1, 2, 3)), "double descending order mismatch");
    }

    static void testEmptyView() {
        ConcurrentNavigableMap<Integer, String> d =
                new ConcurrentSkipListMap<Integer, String>().descendingMap();
        check(d.isEmpty(), "descending view of empty map should be empty");
        check(d.size() == 0, "descending view of empty map should have size 0");
    }
}

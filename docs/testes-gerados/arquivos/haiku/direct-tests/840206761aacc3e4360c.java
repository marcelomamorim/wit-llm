/*
 * @test
 * @summary Verifies ConcurrentSkipListMap values().removeIf for full maps, submaps, no-match and null filter
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Predicate;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) throw new RuntimeException(msg);
    }

    static ConcurrentSkipListMap<Integer, Integer> newMap() {
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 6; i++) {
            map.put(i, i * 10);
        }
        return map;
    }

    static List<Integer> keys(Map<Integer, Integer> map) {
        return new ArrayList<>(map.keySet());
    }

    static void testFullMapRemoval() {
        ConcurrentSkipListMap<Integer, Integer> map = newMap();
        Predicate<Integer> p = v -> v % 20 == 0;
        boolean r = map.values().removeIf(p);
        check(r, "removeIf should return true when entries are removed");
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(3);
        expected.add(5);
        check(keys(map).equals(expected), "remaining keys wrong: " + keys(map));
    }

    static void testNoMatch() {
        ConcurrentSkipListMap<Integer, Integer> map = newMap();
        boolean r = map.values().removeIf(v -> v > 1000);
        check(!r, "removeIf should return false when nothing matches");
        check(map.size() == 6, "map size should be unchanged");
    }

    static void testNullFilter() {
        ConcurrentSkipListMap<Integer, Integer> map = newMap();
        boolean thrown = false;
        try {
            map.values().removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null filter should throw NullPointerException");
        check(map.size() == 6, "map should be unchanged after null filter");
    }

    static void testSubMapRemoval() {
        ConcurrentSkipListMap<Integer, Integer> map = newMap();
        boolean r = map.headMap(4).values().removeIf(v -> v > 15);
        check(r, "submap removeIf should return true when entries are removed");
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(4);
        expected.add(5);
        expected.add(6);
        check(keys(map).equals(expected), "submap removal affected wrong keys: " + keys(map));
    }

    public static void main(String[] args) {
        testFullMapRemoval();
        testNoMatch();
        testNullFilter();
        testSubMapRemoval();
    }
}

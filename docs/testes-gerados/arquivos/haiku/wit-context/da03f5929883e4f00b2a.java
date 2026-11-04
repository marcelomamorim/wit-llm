/*
 * @test
 * @summary Checks public behavior of ConcurrentSkipListMap.descendingKeySet()
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testEmptyMap();
        testDescendingOrder();
        testFirstAndLast();
        testViewReflectsMapChanges();
        testReverseComparatorMap();
    }

    static void testEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        NavigableSet<Integer> set = map.descendingKeySet();
        check(set != null, "descendingKeySet must not return null");
        check(set.isEmpty(), "descending key set of empty map must be empty");
        check(set.size() == 0, "size of empty descending key set must be 0");
        Iterator<Integer> it = set.iterator();
        check(!it.hasNext(), "iterator of empty descending key set must be exhausted");
    }

    static void testDescendingOrder() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(2, "b");
        NavigableSet<Integer> set = map.descendingKeySet();
        check(set.size() == 3, "size must be 3");
        List<Integer> keys = drain(set);
        check(keys.equals(List.of(3, 2, 1)) || keys.equals(toList(3, 2, 1)),
              "keys must be in descending order, got " + keys);
    }

    static void testFirstAndLast() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "x");
        map.put(20, "y");
        map.put(5, "z");
        NavigableSet<Integer> set = map.descendingKeySet();
        check(set.first().equals(20), "first of descending key set must be the largest key");
        check(set.last().equals(5), "last of descending key set must be the smallest key");
    }

    static void testViewReflectsMapChanges() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        NavigableSet<Integer> set = map.descendingKeySet();

        map.put(4, "d");
        check(set.contains(4), "view must reflect key added to map");

        map.remove(2);
        check(!set.contains(2), "view must reflect key removed from map");

        set.remove(3);
        check(!map.containsKey(3), "removing from view must remove mapping from map");

        List<Integer> keys = drain(set);
        check(keys.equals(toList(4, 1)), "unexpected keys after changes: " + keys);
    }

    static void testReverseComparatorMap() {
        ConcurrentSkipListMap<Integer, String> map =
            new ConcurrentSkipListMap<>(Collections.reverseOrder());
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        NavigableSet<Integer> set = map.descendingKeySet();
        List<Integer> keys = drain(set);
        check(keys.equals(toList(1, 2, 3)),
              "descending view of reverse-ordered map must be ascending natural order, got " + keys);
    }

    static List<Integer> drain(NavigableSet<Integer> set) {
        List<Integer> result = new ArrayList<>();
        for (Integer k : set) {
            result.add(k);
        }
        return result;
    }

    static List<Integer> toList(Integer... values) {
        List<Integer> list = new ArrayList<>();
        for (Integer v : values) {
            list.add(v);
        }
        return list;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

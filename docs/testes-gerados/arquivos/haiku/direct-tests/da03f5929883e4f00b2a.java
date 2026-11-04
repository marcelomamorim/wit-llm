/*
 * @test
 * @summary Verify descendingKeySet ordering and view behavior of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testEmptyMap();
        testDescendingOrder();
        testViewReflectsPut();
        testRemoveThroughView();
        testCustomComparator();
        testFirstAndLast();
        System.out.println("PASS");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static List<Integer> toList(NavigableSet<Integer> set) {
        List<Integer> result = new ArrayList<>();
        for (Integer i : set) {
            result.add(i);
        }
        return result;
    }

    static void testEmptyMap() {
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        NavigableSet<Integer> set = map.descendingKeySet();
        check(set != null, "descendingKeySet returned null");
        check(set.isEmpty(), "descending key set of empty map should be empty");
        check(set.size() == 0, "size of descending key set of empty map should be 0");
    }

    static void testDescendingOrder() {
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        map.put(1, 1);
        map.put(3, 3);
        map.put(2, 2);
        NavigableSet<Integer> set = map.descendingKeySet();
        check(toList(set).equals(Arrays.asList(3, 2, 1)),
              "expected descending order [3, 2, 1] but got " + toList(set));
    }

    static void testViewReflectsPut() {
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        map.put(1, 1);
        NavigableSet<Integer> set = map.descendingKeySet();
        map.put(5, 5);
        check(set.contains(5), "view should reflect key added to backing map");
        check(toList(set).equals(Arrays.asList(5, 1)),
              "expected [5, 1] but got " + toList(set));
    }

    static void testRemoveThroughView() {
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        NavigableSet<Integer> set = map.descendingKeySet();
        check(set.remove(2), "remove of present key through view should return true");
        check(!map.containsKey(2), "key removed through view should be absent from map");
        check(map.size() == 2, "map size should be 2 after removal through view");
    }

    static void testCustomComparator() {
        ConcurrentSkipListMap<Integer, Integer> map =
            new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        NavigableSet<Integer> set = map.descendingKeySet();
        check(toList(set).equals(Arrays.asList(1, 2, 3)),
              "with reverse comparator descending view should be [1, 2, 3] but got " + toList(set));
    }

    static void testFirstAndLast() {
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        NavigableSet<Integer> set = map.descendingKeySet();
        check(set.first() == 3, "first of descending key set should be largest key");
        check(set.last() == 1, "last of descending key set should be smallest key");
    }
}

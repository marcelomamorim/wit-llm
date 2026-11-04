/*
 * @test
 * @summary Verifies TreeMap descendingMap().tailMap(fromKey, inclusive) ordering, inclusiveness and out-of-range behavior
 * @run main TreeMapWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static List<Integer> keys(NavigableMap<Integer, String> m) {
        List<Integer> result = new ArrayList<>();
        for (Iterator<Integer> it = m.keySet().iterator(); it.hasNext(); ) {
            result.add(it.next());
        }
        return result;
    }

    static TreeMap<Integer, String> base() {
        TreeMap<Integer, String> t = new TreeMap<>();
        for (int i = 1; i <= 5; i++) {
            t.put(i, "v" + i);
        }
        return t;
    }

    static void testInclusive() {
        NavigableMap<Integer, String> d = base().descendingMap().tailMap(3, true);
        List<Integer> k = keys(d);
        check(k.equals(List.of(3, 2, 1)), "inclusive tailMap expected [3,2,1] but was " + k);
    }

    static void testExclusive() {
        NavigableMap<Integer, String> d = base().descendingMap().tailMap(3, false);
        List<Integer> k = keys(d);
        check(k.equals(List.of(2, 1)), "exclusive tailMap expected [2,1] but was " + k);
    }

    static void testSizeAndContainment() {
        NavigableMap<Integer, String> d = base().descendingMap().tailMap(4, true);
        check(d.size() == 4, "expected size 4 but was " + d.size());
        check(d.containsKey(4), "view should contain 4");
        check(!d.containsKey(5), "view should not contain 5");
    }

    static void testOutOfRangeOnSubMap() {
        TreeMap<Integer, String> t = base();
        NavigableMap<Integer, String> sub = t.subMap(2, true, 4, true).descendingMap();
        boolean thrown = false;
        try {
            sub.tailMap(5, true);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "tailMap with key outside sub-range should throw IllegalArgumentException");
    }

    static void testInRangeOnSubMap() {
        TreeMap<Integer, String> t = base();
        NavigableMap<Integer, String> sub = t.subMap(2, true, 4, true).descendingMap();
        List<Integer> k = keys(sub.tailMap(3, true));
        check(k.equals(List.of(3, 2)), "expected [3,2] but was " + k);
    }

    public static void main(String[] args) {
        testInclusive();
        testExclusive();
        testSizeAndContainment();
        testOutOfRangeOnSubMap();
        testInRangeOnSubMap();
    }
}

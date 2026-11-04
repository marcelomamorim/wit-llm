/*
 * @test
 * @summary Observable behavior of TreeMap.headMap(K, boolean) on a plain (non-submap) TreeMap
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        TreeMap<Integer, String> m = new TreeMap<>();
        for (int i = 1; i <= 5; i++) {
            m.put(i, "v" + i);
        }

        NavigableMap<Integer, String> inclusive = m.headMap(3, true);
        check("[1, 2, 3]".equals(inclusive.keySet().toString()),
              "headMap(3, true) keys: " + inclusive.keySet());

        NavigableMap<Integer, String> exclusive = m.headMap(3, false);
        check("[1, 2]".equals(exclusive.keySet().toString()),
              "headMap(3, false) keys: " + exclusive.keySet());

        NavigableMap<Integer, String> emptyExclusive = m.headMap(1, false);
        check(emptyExclusive.isEmpty(),
              "headMap(1, false) should be empty");

        NavigableMap<Integer, String> belowAll = m.headMap(0, true);
        check(belowAll.isEmpty(),
              "headMap(0, true) should be empty");

        NavigableMap<Integer, String> aboveAll = m.headMap(10, true);
        check("[1, 2, 3, 4, 5]".equals(aboveAll.keySet().toString()),
              "headMap(10, true) keys: " + aboveAll.keySet());

        NavigableMap<Integer, String> view = m.headMap(4, true);
        check(view.containsKey(4) && !view.containsKey(5),
              "headMap(4, true) membership incorrect");
        check("v2".equals(view.get(2)),
              "headMap(4, true).get(2) should return value");

        TreeMap<Integer, String> reversed = new TreeMap<>(Comparator.reverseOrder());
        for (int i = 1; i <= 5; i++) {
            reversed.put(i, "r" + i);
        }
        NavigableMap<Integer, String> reversedHead = reversed.headMap(3, true);
        check("[5, 4, 3]".equals(reversedHead.keySet().toString()),
              "reverse headMap(3, true) keys: " + reversedHead.keySet());

        NavigableMap<Integer, String> reversedExclusive = reversed.headMap(3, false);
        check("[5, 4]".equals(reversedExclusive.keySet().toString()),
              "reverse headMap(3, false) keys: " + reversedExclusive.keySet());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}

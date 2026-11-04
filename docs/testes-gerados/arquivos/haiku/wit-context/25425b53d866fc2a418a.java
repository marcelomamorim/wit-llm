/*
 * @test
 * @summary Public headMap contract of TreeMap: range view, live backing, out-of-range put, comparator ordering
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        m.put(4, "d");

        SortedMap<Integer, String> h = m.headMap(3);
        check(h.size() == 2, "headMap(3) size expected 2");
        check(h.containsKey(1) && h.containsKey(2), "headMap(3) must contain 1 and 2");
        check(!h.containsKey(3) && !h.containsKey(4), "headMap(3) must exclude 3 and 4");
        check(Integer.valueOf(1).equals(h.firstKey()), "firstKey expected 1");
        check(Integer.valueOf(2).equals(h.lastKey()), "lastKey expected 2");

        m.put(0, "z");
        check(h.containsKey(0), "view must reflect insertion into backing map");
        check(h.size() == 3, "view size must reflect backing map");

        check(m.headMap(0).isEmpty(), "headMap of smallest key must be empty");

        boolean thrown = false;
        try {
            h.put(5, "x");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "put outside headMap range must throw IllegalArgumentException");
        check(!m.containsKey(5), "rejected put must not modify backing map");

        TreeMap<String, Integer> r = new TreeMap<>(Comparator.reverseOrder());
        r.put("a", 1);
        r.put("b", 2);
        r.put("c", 3);
        SortedMap<String, Integer> rh = r.headMap("b");
        check(rh.size() == 1, "reverse-order headMap(\"b\") size expected 1");
        check(rh.containsKey("c") && !rh.containsKey("a"), "reverse-order headMap must contain only c");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

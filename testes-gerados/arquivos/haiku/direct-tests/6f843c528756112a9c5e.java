/*
 * @test
 * @summary Verifies TreeMap tailMap(K, boolean) views: range contents and IllegalArgumentException for keys outside a submap range
 * @run main TreeMapWitupTest
 */

import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        TreeMap<Integer, String> tm = new TreeMap<>();
        for (int i = 0; i < 10; i++) {
            tm.put(i, "v" + i);
        }

        NavigableMap<Integer, String> inclusiveTail = tm.tailMap(5, true);
        check(inclusiveTail.size() == 5, "inclusive tailMap size expected 5 but was " + inclusiveTail.size());
        check(inclusiveTail.firstKey() == 5, "inclusive tailMap first key expected 5");
        check(inclusiveTail.lastKey() == 9, "inclusive tailMap last key expected 9");

        NavigableMap<Integer, String> exclusiveTail = tm.tailMap(5, false);
        check(exclusiveTail.size() == 4, "exclusive tailMap size expected 4 but was " + exclusiveTail.size());
        check(exclusiveTail.firstKey() == 6, "exclusive tailMap first key expected 6");
        check(!exclusiveTail.containsKey(5), "exclusive tailMap must not contain 5");

        NavigableMap<Integer, String> head = tm.headMap(5, false);
        check(head.size() == 5, "headMap size expected 5 but was " + head.size());

        NavigableMap<Integer, String> subTail = head.tailMap(2, true);
        check(subTail.size() == 3, "submap tailMap size expected 3 but was " + subTail.size());
        check(subTail.firstKey() == 2, "submap tailMap first key expected 2");
        check(subTail.lastKey() == 4, "submap tailMap last key expected 4");

        boolean thrown = false;
        try {
            head.tailMap(5, true);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "tailMap with fromKey above submap upper bound must throw IllegalArgumentException");
    }
}

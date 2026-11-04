/*
 * @test
 * @summary Verifies iterator remove() on TreeMap sub-map views (ascending and descending) removes the last returned key from the backing map
 * @run main TreeMapWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static TreeMap<Integer, String> newMap() {
        TreeMap<Integer, String> m = new TreeMap<>();
        for (int i = 1; i <= 5; i++) {
            m.put(i, "v" + i);
        }
        return m;
    }

    static void testAscendingSubMapRemove() {
        TreeMap<Integer, String> m = newMap();
        NavigableMap<Integer, String> sub = m.subMap(2, true, 4, true);
        Iterator<Integer> it = sub.keySet().iterator();
        check(it.next() == 2, "first ascending key should be 2");
        it.remove();
        check(!m.containsKey(2), "key 2 should be removed from backing map");
        check(m.size() == 4, "backing map size should be 4");
        check(m.containsKey(3) && m.containsKey(4), "other sub-map keys must remain");
    }

    static void testAscendingRemoveAllInSubMap() {
        TreeMap<Integer, String> m = newMap();
        NavigableMap<Integer, String> sub = m.subMap(2, true, 4, true);
        Iterator<Integer> it = sub.keySet().iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        List<Integer> remaining = new ArrayList<>(m.keySet());
        check(remaining.size() == 2, "two keys should remain, got " + remaining);
        check(remaining.get(0) == 1 && remaining.get(1) == 5, "remaining keys should be 1 and 5");
    }

    static void testDescendingRemove() {
        TreeMap<Integer, String> m = newMap();
        NavigableMap<Integer, String> desc = m.descendingMap();
        Iterator<Integer> it = desc.keySet().iterator();
        check(it.next() == 5, "first descending key should be 5");
        it.remove();
        check(!m.containsKey(5), "key 5 should be removed from backing map");
        check(m.size() == 4, "backing map size should be 4 after descending remove");
    }

    static void testRemoveBeforeNextThrows() {
        TreeMap<Integer, String> m = newMap();
        NavigableMap<Integer, String> sub = m.subMap(2, true, 4, true);
        Iterator<Integer> it = sub.keySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() should throw IllegalStateException");
        check(m.size() == 5, "failed remove must not change the map");
    }

    public static void main(String[] args) {
        testAscendingSubMapRemove();
        testAscendingRemoveAllInSubMap();
        testDescendingRemove();
        testRemoveBeforeNextThrows();
    }
}

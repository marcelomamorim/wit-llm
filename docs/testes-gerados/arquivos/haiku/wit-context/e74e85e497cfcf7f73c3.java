/*
 * @test
 * @summary Verifies public iterator remove() behavior on TreeMap sub-map entry sets (IllegalStateException and ConcurrentModificationException paths).
 * @run main TreeMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsIllegalStateException();
        testRemoveAfterNextRemovesEntry();
        testSecondRemoveWithoutNextThrowsIllegalStateException();
        testRemoveAfterStructuralModificationThrowsConcurrentModificationException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static TreeMap<Integer, String> newMap() {
        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 1; i <= 5; i++) {
            map.put(i, "v" + i);
        }
        return map;
    }

    static void testRemoveBeforeNextThrowsIllegalStateException() {
        TreeMap<Integer, String> map = newMap();
        NavigableMap<Integer, String> sub = map.subMap(1, true, 5, true);
        Iterator<Map.Entry<Integer, String>> it = sub.entrySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(map.size() == 5, "map must be unchanged after failed remove()");
    }

    static void testRemoveAfterNextRemovesEntry() {
        TreeMap<Integer, String> map = newMap();
        NavigableMap<Integer, String> sub = map.subMap(1, true, 5, true);
        Iterator<Map.Entry<Integer, String>> it = sub.entrySet().iterator();
        Map.Entry<Integer, String> first = it.next();
        check(first.getKey() == 1, "first ascending key must be 1");
        it.remove();
        check(!map.containsKey(1), "key 1 must be removed from backing map");
        check(map.size() == 4, "size must decrease by one");
        check(map.containsKey(2) && map.containsKey(5), "other keys must remain");
    }

    static void testSecondRemoveWithoutNextThrowsIllegalStateException() {
        TreeMap<Integer, String> map = newMap();
        NavigableMap<Integer, String> sub = map.subMap(1, true, 5, true);
        Iterator<Map.Entry<Integer, String>> it = sub.entrySet().iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove() without next() must throw IllegalStateException");
        check(map.size() == 4, "size must reflect only one removal");
    }

    static void testRemoveAfterStructuralModificationThrowsConcurrentModificationException() {
        TreeMap<Integer, String> map = newMap();
        NavigableMap<Integer, String> sub = map.subMap(1, true, 5, true);
        Iterator<Map.Entry<Integer, String>> it = sub.entrySet().iterator();
        it.next();
        map.put(10, "x");
        boolean thrown = false;
        try {
            it.remove();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "remove() after structural modification must throw ConcurrentModificationException");
        check(map.containsKey(10), "concurrent insert must remain in map");
    }
}

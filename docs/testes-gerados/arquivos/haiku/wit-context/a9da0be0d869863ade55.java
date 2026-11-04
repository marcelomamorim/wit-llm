/*
 * @test
 * @summary Verifies observable Iterator.remove() behavior for descending TreeMap views (IllegalStateException and ConcurrentModificationException paths).
 * @run main TreeMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutNextThrowsIllegalState();
        testRemoveAfterNextRemovesDescendingEntry();
        testRemoveTwiceThrowsIllegalState();
        testRemoveAfterStructuralModificationThrowsCME();
        testKeySetIteratorRemove();
    }

    static NavigableMap<String, Integer> newMap() {
        TreeMap<String, Integer> tm = new TreeMap<String, Integer>();
        tm.put("a", 1);
        tm.put("b", 2);
        tm.put("c", 3);
        return tm;
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testRemoveWithoutNextThrowsIllegalState() {
        NavigableMap<String, Integer> tm = newMap();
        Iterator<Map.Entry<String, Integer>> it = tm.descendingMap().entrySet().iterator();
        try {
            it.remove();
            fail("Expected IllegalStateException when remove() is called before next()");
        } catch (IllegalStateException expected) {
            // expected
        }
        if (tm.size() != 3) {
            fail("Map size changed after failed remove(): " + tm.size());
        }
    }

    static void testRemoveAfterNextRemovesDescendingEntry() {
        NavigableMap<String, Integer> tm = newMap();
        Iterator<Map.Entry<String, Integer>> it = tm.descendingMap().entrySet().iterator();
        Map.Entry<String, Integer> first = it.next();
        if (!"c".equals(first.getKey())) {
            fail("Expected first descending key 'c' but was " + first.getKey());
        }
        it.remove();
        if (tm.containsKey("c")) {
            fail("Key 'c' should have been removed");
        }
        if (tm.size() != 2) {
            fail("Expected size 2 but was " + tm.size());
        }
        if (!tm.containsKey("a") || !tm.containsKey("b")) {
            fail("Other keys should remain");
        }
    }

    static void testRemoveTwiceThrowsIllegalState() {
        NavigableMap<String, Integer> tm = newMap();
        Iterator<Map.Entry<String, Integer>> it = tm.descendingMap().entrySet().iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            fail("Expected IllegalStateException on second consecutive remove()");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testRemoveAfterStructuralModificationThrowsCME() {
        NavigableMap<String, Integer> tm = newMap();
        Iterator<Map.Entry<String, Integer>> it = tm.descendingMap().entrySet().iterator();
        it.next();
        tm.put("d", 4);
        try {
            it.remove();
            fail("Expected ConcurrentModificationException after structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testKeySetIteratorRemove() {
        NavigableMap<String, Integer> tm = newMap();
        Iterator<String> it = tm.descendingKeySet().iterator();
        String first = it.next();
        if (!"c".equals(first)) {
            fail("Expected first descending key 'c' but was " + first);
        }
        it.remove();
        if (tm.containsKey("c") || tm.size() != 2) {
            fail("descendingKeySet iterator remove did not remove the key");
        }
    }
}

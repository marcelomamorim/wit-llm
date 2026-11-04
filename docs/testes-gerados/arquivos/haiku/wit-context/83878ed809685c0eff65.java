/*
 * @test
 * @summary Verifies IllegalStateException and ConcurrentModificationException from iterator remove on TreeMap submap key views
 * @run main TreeMapWitupTest
 */
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsISE();
        testRemoveAfterNextRemovesEntry();
        testRemoveTwiceThrowsISE();
        testStructuralModificationThrowsCME();
    }

    static TreeMap<Integer, String> newMap() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        return m;
    }

    static void testRemoveBeforeNextThrowsISE() {
        TreeMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.subMap(1, 4).keySet().iterator();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        if (m.size() != 3) {
            throw new RuntimeException("map changed size: " + m.size());
        }
    }

    static void testRemoveAfterNextRemovesEntry() {
        TreeMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.subMap(1, 4).keySet().iterator();
        Integer first = it.next();
        if (first.intValue() != 1) {
            throw new RuntimeException("unexpected first key: " + first);
        }
        it.remove();
        if (m.size() != 2 || m.containsKey(1)) {
            throw new RuntimeException("entry not removed correctly");
        }
    }

    static void testRemoveTwiceThrowsISE() {
        TreeMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.subMap(1, 4).keySet().iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException on second remove");
        } catch (IllegalStateException expected) {
            // expected
        }
        if (m.size() != 2) {
            throw new RuntimeException("map changed size: " + m.size());
        }
    }

    static void testStructuralModificationThrowsCME() {
        TreeMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.subMap(1, 4).keySet().iterator();
        it.next();
        m.put(10, "x");
        try {
            it.remove();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}

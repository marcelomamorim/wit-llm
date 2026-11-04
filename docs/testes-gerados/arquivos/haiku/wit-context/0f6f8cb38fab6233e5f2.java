/*
 * @test
 * @summary Verifies TreeMap iterator remove() preconditions through public keySet iterator
 * @run main TreeMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsIllegalStateException();
        testRemoveAfterNextRemovesKey();
        testRemoveAfterStructuralModificationThrowsConcurrentModificationException();
        testRemoveTwiceThrowsIllegalStateException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testRemoveBeforeNextThrowsIllegalStateException() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        Iterator<String> it = map.keySet().iterator();
        try {
            it.remove();
            throw new AssertionError("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(map.size() == 1, "map must be unchanged");
    }

    static void testRemoveAfterNextRemovesKey() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Iterator<String> it = map.keySet().iterator();
        String first = it.next();
        check("a".equals(first), "first key must be a");
        it.remove();
        check(!map.containsKey("a"), "removed key must be absent");
        check(map.size() == 2, "size must be 2 after remove");
        check("b".equals(map.firstKey()), "new first key must be b");
    }

    static void testRemoveAfterStructuralModificationThrowsConcurrentModificationException() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Iterator<String> it = map.keySet().iterator();
        it.next();
        map.put("z", 3);
        try {
            it.remove();
            throw new AssertionError("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testRemoveTwiceThrowsIllegalStateException() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Iterator<String> it = map.keySet().iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new AssertionError("expected IllegalStateException on second remove");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(map.size() == 1, "only one removal must take effect");
    }
}

/*
 * @test
 * @summary White-box-free checks of WeakHashMap HashIterator.remove() preconditions and exceptions via public keySet().iterator()
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsIllegalStateException();
        testRemoveTwiceThrowsIllegalStateException();
        testRemoveAfterConcurrentModificationThrowsCME();
        testRemoveAfterNextRemovesEntry();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveBeforeNextThrowsIllegalStateException() {
        List<String> keys = new ArrayList<>();
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        String k1 = new String("k1");
        keys.add(k1);
        m.put(k1, 1);
        Iterator<String> it = m.keySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(m.containsKey(k1), "map must be unchanged after failed remove()");
        check(m.size() == 1, "size must be unchanged after failed remove()");
        check(!keys.isEmpty(), "keys must stay reachable");
    }

    static void testRemoveTwiceThrowsIllegalStateException() {
        List<String> keys = new ArrayList<>();
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        String k1 = new String("k1");
        String k2 = new String("k2");
        keys.add(k1);
        keys.add(k2);
        m.put(k1, 1);
        m.put(k2, 2);
        Iterator<String> it = m.keySet().iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second consecutive remove() must throw IllegalStateException");
        check(m.size() == 1, "only one entry must have been removed");
        check(!keys.isEmpty(), "keys must stay reachable");
    }

    static void testRemoveAfterConcurrentModificationThrowsCME() {
        List<String> keys = new ArrayList<>();
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        String k1 = new String("k1");
        String k2 = new String("k2");
        String k3 = new String("k3");
        keys.add(k1);
        keys.add(k2);
        keys.add(k3);
        m.put(k1, 1);
        m.put(k2, 2);
        Iterator<String> it = m.keySet().iterator();
        String returned = it.next();
        m.put(k3, 3);
        boolean thrown = false;
        try {
            it.remove();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "remove() after external structural modification must throw ConcurrentModificationException");
        check(m.containsKey(returned), "entry must not be removed when CME is thrown");
        check(m.containsKey(k3), "externally added entry must remain");
        check(!keys.isEmpty(), "keys must stay reachable");
    }

    static void testRemoveAfterNextRemovesEntry() {
        List<String> keys = new ArrayList<>();
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        String k1 = new String("k1");
        String k2 = new String("k2");
        keys.add(k1);
        keys.add(k2);
        m.put(k1, 1);
        m.put(k2, 2);
        Iterator<String> it = m.keySet().iterator();
        String returned = it.next();
        it.remove();
        check(!m.containsKey(returned), "remove() after next() must remove the returned key");
        check(m.size() == 1, "exactly one entry must remain");
        check(!keys.isEmpty(), "keys must stay reachable");
    }
}

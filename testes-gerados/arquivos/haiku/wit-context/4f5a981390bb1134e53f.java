/*
 * @test
 * @summary Verifies HashMap iterator remove() exception behavior for IllegalStateException and ConcurrentModificationException, and its normal removal effect
 * @run main HashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsIllegalStateException();
        testRemoveTwiceThrowsIllegalStateException();
        testRemoveAfterNextRemovesMapping();
        testRemoveAfterStructuralModificationThrowsConcurrentModificationException();
    }

    static void testRemoveBeforeNextThrowsIllegalStateException() {
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        Iterator<String> it = m.keySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("remove() before next() should throw IllegalStateException");
        }
        if (m.size() != 1 || !m.containsKey("a")) {
            fail("map must be unchanged after failed remove()");
        }
    }

    static void testRemoveTwiceThrowsIllegalStateException() {
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        Iterator<String> it = m.keySet().iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("second consecutive remove() should throw IllegalStateException");
        }
        if (m.size() != 1) {
            fail("only one mapping should have been removed, size=" + m.size());
        }
    }

    static void testRemoveAfterNextRemovesMapping() {
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        Iterator<String> it = m.keySet().iterator();
        String k = it.next();
        it.remove();
        if (m.containsKey(k)) {
            fail("key returned by next() should be removed from map");
        }
        if (m.size() != 1) {
            fail("unexpected size after remove(): " + m.size());
        }
    }

    static void testRemoveAfterStructuralModificationThrowsConcurrentModificationException() {
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        Iterator<String> it = m.keySet().iterator();
        it.next();
        m.put("z", 9);
        boolean thrown = false;
        try {
            it.remove();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("remove() after structural modification should throw ConcurrentModificationException");
        }
    }

    static void fail(String msg) {
        throw new AssertionError(msg);
    }
}

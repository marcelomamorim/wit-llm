/*
 * @test
 * @summary Verifies WeakHashMap iterator remove() state checks and effects through public iterators
 * @run main WeakHashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    static final String K1 = new String("k1");
    static final String K2 = new String("k2");
    static final String K3 = new String("k3");

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsIllegalState();
        testRemoveAfterNextRemovesMapping();
        testSecondRemoveThrowsIllegalState();
        testRemoveAfterConcurrentModificationThrows();
        testEntrySetIteratorRemoveRemovesMapping();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveBeforeNextThrowsIllegalState() {
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        m.put(K1, 1);
        Iterator<String> it = m.keySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(m.containsKey(K1), "mapping must not be removed when remove() fails");
    }

    static void testRemoveAfterNextRemovesMapping() {
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        m.put(K1, 1);
        m.put(K2, 2);
        Iterator<String> it = m.keySet().iterator();
        String first = it.next();
        it.remove();
        check(!m.containsKey(first), "iterator remove() must remove the returned key");
        check(m.size() == 1, "size must decrease by one after iterator remove()");
    }

    static void testSecondRemoveThrowsIllegalState() {
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        m.put(K1, 1);
        m.put(K2, 2);
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
        check(m.size() == 1, "second failing remove() must not change the map");
    }

    static void testRemoveAfterConcurrentModificationThrows() {
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        m.put(K1, 1);
        m.put(K2, 2);
        Iterator<String> it = m.keySet().iterator();
        it.next();
        m.put(K3, 3);
        boolean thrown = false;
        try {
            it.remove();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "remove() after structural modification must throw ConcurrentModificationException");
        check(m.containsKey(K1), "mapping must not be removed when remove() throws CME");
    }

    static void testEntrySetIteratorRemoveRemovesMapping() {
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        m.put(K1, 1);
        m.put(K2, 2);
        Iterator<java.util.Map.Entry<String, Integer>> it = m.entrySet().iterator();
        java.util.Map.Entry<String, Integer> e = it.next();
        String key = e.getKey();
        it.remove();
        check(!m.containsKey(key), "entry iterator remove() must remove the returned entry's key");
        check(m.size() == 1, "size must decrease after entry iterator remove()");
    }
}

/*
 * @test
 * @summary WIT-derived behavioral checks for WeakHashMap key iteration via keySet().iterator() (next())
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapNextThrowsNoSuchElement();
        testExhaustedIteratorNextThrowsNoSuchElement();
        testConcurrentModificationAfterPut();
        testNullKeyIsReturnedByNext();
        testIterateAllKeysThenExhaust();
    }

    static void testEmptyMapNextThrowsNoSuchElement() {
        WeakHashMap<Object, Object> m = new WeakHashMap<>();
        Iterator<Object> it = m.keySet().iterator();
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testExhaustedIteratorNextThrowsNoSuchElement() {
        WeakHashMap<Object, Object> m = new WeakHashMap<>();
        List<Object> keys = new ArrayList<>();
        Object k1 = new Object();
        keys.add(k1);
        m.put(k1, "v1");
        Iterator<Object> it = m.keySet().iterator();
        Object returned = it.next();
        if (returned != k1) {
            throw new RuntimeException("unexpected key returned");
        }
        if (it.hasNext()) {
            throw new RuntimeException("iterator should be exhausted");
        }
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
        if (keys.size() != 1) {
            throw new RuntimeException("keys must stay reachable");
        }
    }

    static void testConcurrentModificationAfterPut() {
        WeakHashMap<Object, Object> m = new WeakHashMap<>();
        List<Object> keys = new ArrayList<>();
        Object k1 = new Object();
        Object k2 = new Object();
        keys.add(k1);
        keys.add(k2);
        m.put(k1, "v1");
        m.put(k2, "v2");
        Iterator<Object> it = m.keySet().iterator();
        Object extra = new Object();
        keys.add(extra);
        m.put(extra, "v3");
        try {
            it.next();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testNullKeyIsReturnedByNext() {
        WeakHashMap<Object, Object> m = new WeakHashMap<>();
        m.put(null, "nullValue");
        Iterator<Object> it = m.keySet().iterator();
        Object k = it.next();
        if (k != null) {
            throw new RuntimeException("expected null key");
        }
        if (it.hasNext()) {
            throw new RuntimeException("iterator should be exhausted");
        }
    }

    static void testIterateAllKeysThenExhaust() {
        WeakHashMap<Object, Object> m = new WeakHashMap<>();
        List<Object> keys = new ArrayList<>();
        Object k1 = new Object();
        Object k2 = new Object();
        Object k3 = new Object();
        keys.add(k1);
        keys.add(k2);
        keys.add(k3);
        m.put(k1, "a");
        m.put(k2, "b");
        m.put(k3, "c");
        Set<Object> seen = new HashSet<>();
        Iterator<Object> it = m.keySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            seen.add(it.next());
            count++;
        }
        if (count != 3 || seen.size() != 3) {
            throw new RuntimeException("expected 3 distinct keys, got " + count);
        }
        if (!seen.contains(k1) || !seen.contains(k2) || !seen.contains(k3)) {
            throw new RuntimeException("missing key during iteration");
        }
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}

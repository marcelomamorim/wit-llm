/*
 * @test
 * @summary Verifies IdentityHashMap key iterator next() identity semantics and its concurrent modification and exhaustion behavior
 * @run main IdentityHashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testKeyIteratorReturnsIdentityKeys();
        testNextThrowsConcurrentModificationExceptionAfterStructuralChange();
        testNextThrowsNoSuchElementWhenExhausted();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testKeyIteratorReturnsIdentityKeys() {
        String a = new String("k");
        String b = new String("k");
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        map.put(a, Integer.valueOf(1));
        map.put(b, Integer.valueOf(2));
        Iterator<Object> it = map.keySet().iterator();
        int count = 0;
        boolean sawA = false;
        boolean sawB = false;
        while (it.hasNext()) {
            Object k = it.next();
            count++;
            if (k == a) {
                sawA = true;
            }
            if (k == b) {
                sawB = true;
            }
        }
        check(count == 2, "expected 2 keys, got " + count);
        check(sawA && sawB, "iterator must return both identity-distinct keys");
    }

    static void testNextThrowsConcurrentModificationExceptionAfterStructuralChange() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        map.put(new Object(), "first");
        map.put(new Object(), "second");
        Iterator<Object> it = map.keySet().iterator();
        it.next();
        map.put(new Object(), "third");
        try {
            it.next();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testNextThrowsNoSuchElementWhenExhausted() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        map.put(new Object(), "only");
        Iterator<Object> it = map.keySet().iterator();
        it.next();
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}

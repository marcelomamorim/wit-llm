/*
 * @test
 * @summary Checks public iterator behavior of IdentityHashMap.values() that reaches ValueIterator.next(), including ConcurrentModificationException after structural modification
 * @run main IdentityHashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsValues();
        testNextReturnsNullValue();
        testNextThrowsCMEAfterStructuralModification();
        testNextThrowsNoSuchElementWhenExhausted();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNextReturnsValues() {
        IdentityHashMap<Object, String> map = new IdentityHashMap<>();
        Object k1 = new Object();
        Object k2 = new Object();
        map.put(k1, "v1");
        map.put(k2, "v2");
        Iterator<String> it = map.values().iterator();
        int count = 0;
        boolean sawV1 = false;
        boolean sawV2 = false;
        while (it.hasNext()) {
            String v = it.next();
            count++;
            if ("v1".equals(v)) {
                sawV1 = true;
            }
            if ("v2".equals(v)) {
                sawV2 = true;
            }
        }
        check(count == 2, "expected 2 values, got " + count);
        check(sawV1 && sawV2, "expected both values to be returned");
    }

    static void testNextReturnsNullValue() {
        IdentityHashMap<Object, String> map = new IdentityHashMap<>();
        map.put(new Object(), null);
        Iterator<String> it = map.values().iterator();
        check(it.hasNext(), "expected one element");
        check(it.next() == null, "expected null value from next()");
        check(!it.hasNext(), "expected no more elements");
    }

    static void testNextThrowsCMEAfterStructuralModification() {
        IdentityHashMap<Object, String> map = new IdentityHashMap<>();
        map.put(new Object(), "a");
        map.put(new Object(), "b");
        Iterator<String> it = map.values().iterator();
        it.next();
        map.put(new Object(), "c");
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural modification");
    }

    static void testNextThrowsNoSuchElementWhenExhausted() {
        IdentityHashMap<Object, String> map = new IdentityHashMap<>();
        map.put(new Object(), "only");
        Iterator<String> it = map.values().iterator();
        it.next();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "expected NoSuchElementException when exhausted");
    }
}

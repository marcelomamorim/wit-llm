/*
 * @test
 * @summary WeakHashMap values iterator next() observable behavior
 * @run main WeakHashMapWitupTest
 */

import java.util.HashSet;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsAllValues();
        testNoSuchElementOnEmptyMap();
        testNoSuchElementWhenExhausted();
        testConcurrentModificationAfterPut();
        testNullValueReturned();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNextReturnsAllValues() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String k1 = "a";
        String k2 = "b";
        map.put(k1, "v1");
        map.put(k2, "v2");
        Iterator<String> it = map.values().iterator();
        Set<String> seen = new HashSet<>();
        seen.add(it.next());
        seen.add(it.next());
        check(seen.size() == 2, "expected two distinct values");
        check(seen.contains("v1"), "missing v1");
        check(seen.contains("v2"), "missing v2");
        check(!it.hasNext(), "iterator should be exhausted");
        check(k1.length() + k2.length() == 2, "keys must stay reachable");
    }

    static void testNoSuchElementOnEmptyMap() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        Iterator<String> it = map.values().iterator();
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException on empty map");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testNoSuchElementWhenExhausted() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String k1 = "a";
        map.put(k1, "v1");
        Iterator<String> it = map.values().iterator();
        check("v1".equals(it.next()), "first next() should return v1");
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException when exhausted");
        } catch (NoSuchElementException expected) {
            // expected
        }
        check(k1.length() == 1, "key must stay reachable");
    }

    static void testConcurrentModificationAfterPut() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String k1 = "a";
        String k2 = "b";
        map.put(k1, "v1");
        Iterator<String> it = map.values().iterator();
        map.put(k2, "v2");
        try {
            it.next();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
        check(k1.length() + k2.length() == 2, "keys must stay reachable");
    }

    static void testNullValueReturned() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String k1 = "a";
        map.put(k1, null);
        Iterator<String> it = map.values().iterator();
        check(it.next() == null, "null value should be returned");
        check(!it.hasNext(), "iterator should be exhausted");
    }
}

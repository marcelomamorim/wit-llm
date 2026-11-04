/*
 * @test
 * @summary Witup-derived checks for HashMap iterator next() exception paths via public keySet iteration
 * @run main HashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsAllKeys();
        testNextOnEmptyMapThrowsNoSuchElementException();
        testNextAfterExhaustionThrowsNoSuchElementException();
        testNextAfterStructuralModificationThrowsConcurrentModificationException();
    }

    static void testNextReturnsAllKeys() {
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        Iterator<String> it = m.keySet().iterator();
        Set<String> seen = new HashSet<>();
        seen.add(it.next());
        seen.add(it.next());
        if (!seen.equals(m.keySet())) {
            throw new RuntimeException("next() did not return all keys: " + seen);
        }
        if (it.hasNext()) {
            throw new RuntimeException("iterator should be exhausted");
        }
    }

    static void testNextOnEmptyMapThrowsNoSuchElementException() {
        Map<String, Integer> m = new HashMap<>();
        Iterator<String> it = m.keySet().iterator();
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testNextAfterExhaustionThrowsNoSuchElementException() {
        Map<String, Integer> m = new HashMap<>();
        m.put("only", 1);
        Iterator<String> it = m.keySet().iterator();
        if (!"only".equals(it.next())) {
            throw new RuntimeException("unexpected first key");
        }
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testNextAfterStructuralModificationThrowsConcurrentModificationException() {
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        Iterator<String> it = m.keySet().iterator();
        it.next();
        m.put("c", 3);
        try {
            it.next();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }
}

/*
 * @test
 * @summary Tests Hashtable iterator next() behavior: returns all elements, detects concurrent structural modification, and stays valid after Iterator.remove
 * @run main HashtableWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNextReturnsAllKeys();
        testNextThrowsCMEAfterPut();
        testNextThrowsCMEAfterRemove();
        testIteratorRemoveKeepsIteratorValid();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNextReturnsAllKeys() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("one", 1);
        h.put("two", 2);
        h.put("three", 3);

        Iterator<String> it = h.keySet().iterator();
        Set<String> seen = new HashSet<>();
        int count = 0;
        while (it.hasNext()) {
            seen.add(it.next());
            count++;
        }
        check(count == 3, "expected 3 elements, got " + count);
        check(seen.equals(h.keySet()), "iterated keys differ from map keys: " + seen);
    }

    static void testNextThrowsCMEAfterPut() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        h.put("b", 2);

        Iterator<String> it = h.keySet().iterator();
        h.put("c", 3);
        try {
            it.next();
            throw new RuntimeException("expected ConcurrentModificationException after put");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testNextThrowsCMEAfterRemove() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        h.put("b", 2);

        Iterator<String> it = h.keySet().iterator();
        h.remove("a");
        try {
            it.next();
            throw new RuntimeException("expected ConcurrentModificationException after remove");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testIteratorRemoveKeepsIteratorValid() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        h.put("b", 2);
        h.put("c", 3);

        Iterator<java.util.Map.Entry<String, Integer>> it = h.entrySet().iterator();
        it.next();
        it.remove();
        it.next();
        it.remove();
        check(h.size() == 1, "expected size 1 after two removals, got " + h.size());
    }
}

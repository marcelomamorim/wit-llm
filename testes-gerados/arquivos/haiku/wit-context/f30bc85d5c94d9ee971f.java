/*
 * @test
 * @summary Checks TreeMap iterator next() behavior: key order, NoSuchElementException on exhaustion, and ConcurrentModificationException after structural modification
 * @run main TreeMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsKeysInOrder();
        testNextOnEmptyMapThrowsNoSuchElement();
        testNextAfterExhaustionThrowsNoSuchElement();
        testNextAfterStructuralModificationThrowsCME();
        testNextAfterExhaustionAndModificationThrowsNSEE();
    }

    static void testNextReturnsKeysInOrder() {
        TreeMap<Integer, Integer> m = new TreeMap<>();
        m.put(3, 30);
        m.put(1, 10);
        m.put(2, 20);
        Iterator<Integer> it = m.keySet().iterator();
        int k1 = it.next();
        int k2 = it.next();
        int k3 = it.next();
        if (k1 != 1 || k2 != 2 || k3 != 3) {
            fail("keys not returned in sorted order");
        }
        if (it.hasNext()) {
            fail("iterator should be exhausted");
        }
    }

    static void testNextOnEmptyMapThrowsNoSuchElement() {
        Iterator<Integer> it = new TreeMap<Integer, Integer>().keySet().iterator();
        try {
            it.next();
            fail("expected NoSuchElementException on empty map");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testNextAfterExhaustionThrowsNoSuchElement() {
        TreeMap<Integer, Integer> m = new TreeMap<>();
        m.put(1, 10);
        Iterator<Integer> it = m.keySet().iterator();
        it.next();
        try {
            it.next();
            fail("expected NoSuchElementException after exhaustion");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testNextAfterStructuralModificationThrowsCME() {
        TreeMap<Integer, Integer> m = new TreeMap<>();
        m.put(1, 10);
        m.put(2, 20);
        Iterator<Integer> it = m.keySet().iterator();
        it.next();
        m.put(3, 30);
        try {
            it.next();
            fail("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }

    static void testNextAfterExhaustionAndModificationThrowsNSEE() {
        TreeMap<Integer, Integer> m = new TreeMap<>();
        m.put(1, 10);
        Iterator<Integer> it = m.keySet().iterator();
        it.next();
        m.put(2, 20);
        try {
            it.next();
            fail("expected NoSuchElementException when exhausted before modification check");
        } catch (NoSuchElementException expected) {
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }
}

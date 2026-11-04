/*
 * @test
 * @summary Witup-derived checks for TreeMap descending key iteration (next on exhausted or structurally modified iterator)
 * @run main TreeMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testDescendingOrderNext();
        testExhaustedDescendingIteratorThrows();
        testEmptyMapDescendingNextThrows();
        testConcurrentModificationAfterStructuralChange();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDescendingOrderNext() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        Iterator<Integer> it = m.descendingKeySet().iterator();
        check(it.hasNext(), "iterator should have next");
        check(it.next() == 3, "first descending key should be 3");
        check(it.next() == 2, "second descending key should be 2");
        check(it.next() == 1, "third descending key should be 1");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testExhaustedDescendingIteratorThrows() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        Iterator<Integer> it = m.descendingKeySet().iterator();
        check(it.next() == 1, "first descending key should be 1");
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException on exhausted iterator");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testEmptyMapDescendingNextThrows() {
        TreeMap<Integer, String> m = new TreeMap<>();
        Iterator<Integer> it = m.descendingKeySet().iterator();
        check(!it.hasNext(), "empty map iterator should have no next");
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException on empty map");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testConcurrentModificationAfterStructuralChange() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        Iterator<Integer> it = m.descendingKeySet().iterator();
        check(it.next() == 3, "first descending key should be 3 before modification");
        m.put(4, "d");
        try {
            it.next();
            throw new RuntimeException("expected ConcurrentModificationException after structural change");
        } catch (ConcurrentModificationException expected) {
        }
    }
}

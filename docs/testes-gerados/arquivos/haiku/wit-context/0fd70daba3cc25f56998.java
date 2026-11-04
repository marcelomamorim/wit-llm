/*
 * @test
 * @summary WITUP-derived checks for LinkedList iterator remove() via public iterator API
 * @run main LinkedListWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutNextThrowsIllegalState();
        testRemoveAfterNextRemovesElement();
        testRemoveAfterStructuralModificationThrowsCME();
        testRemoveTwiceThrowsIllegalState();
    }

    static void testRemoveWithoutNextThrowsIllegalState() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        Iterator<String> it = list.iterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(list.size() == 1 && "a".equals(list.get(0)), "list must be unchanged");
    }

    static void testRemoveAfterNextRemovesElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        Iterator<String> it = list.iterator();
        check("a".equals(it.next()), "first next() must return a");
        it.remove();
        check(list.size() == 1, "size must be 1 after remove");
        check("b".equals(list.get(0)), "remaining element must be b");
        check(it.hasNext(), "iterator must still have next element");
        check("b".equals(it.next()), "next() after remove must return b");
    }

    static void testRemoveAfterStructuralModificationThrowsCME() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        Iterator<String> it = list.iterator();
        it.next();
        list.add("c");
        try {
            it.remove();
            throw new RuntimeException("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testRemoveTwiceThrowsIllegalState() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        Iterator<String> it = list.iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException on second remove");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

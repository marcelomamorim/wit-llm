/*
 * @test
 * @summary Checks LinkedList ListIterator.set guard behavior through public API
 * @run main LinkedListWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testSetWithoutNextThrowsIllegalState();
        testSetAfterNextUpdatesElement();
        testSetAfterStructuralModificationThrowsConcurrentModification();
        testSetAfterIteratorRemoveThrowsIllegalState();
    }

    static void testSetWithoutNextThrowsIllegalState() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        try {
            it.set("x");
            throw new AssertionError("expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
        if (list.get(0) != "a") {
            throw new AssertionError("list must not change on failed set");
        }
    }

    static void testSetAfterNextUpdatesElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        it.next();
        it.set("x");
        if (!"x".equals(list.get(0))) {
            throw new AssertionError("expected x at index 0 but was " + list.get(0));
        }
        if (!"b".equals(list.get(1))) {
            throw new AssertionError("expected b at index 1 but was " + list.get(1));
        }
    }

    static void testSetAfterStructuralModificationThrowsConcurrentModification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        it.next();
        list.add("c");
        try {
            it.set("y");
            throw new AssertionError("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }

    static void testSetAfterIteratorRemoveThrowsIllegalState() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        it.next();
        it.remove();
        try {
            it.set("z");
            throw new AssertionError("expected IllegalStateException after remove");
        } catch (IllegalStateException expected) {
        }
    }
}

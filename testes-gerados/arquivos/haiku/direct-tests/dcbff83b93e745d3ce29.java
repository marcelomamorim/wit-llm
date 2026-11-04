/*
 * @test
 * @summary Tests LinkedList.ListItr.set(E) observable behavior through listIterator()
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testSetBeforeIterationThrowsIllegalState();
        testSetAfterNextReplacesElement();
        testSetAfterPreviousReplacesElement();
        testSetAcceptsNullElement();
        testSetAfterRemoveThrowsIllegalState();
        testSetAfterExternalModificationThrowsConcurrentModification();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetBeforeIterationThrowsIllegalState() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        boolean thrown = false;
        try {
            it.set("x");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "set before next() must throw IllegalStateException");
        check("a".equals(list.get(0)), "list must be unchanged");
    }

    static void testSetAfterNextReplacesElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ListIterator<String> it = list.listIterator();
        it.next();
        it.set("x");
        check("x".equals(list.get(0)), "set after next() must replace first element");
        check("b".equals(list.get(1)), "other elements must be unchanged");
        check(list.size() == 3, "size must be unchanged");
    }

    static void testSetAfterPreviousReplacesElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ListIterator<String> it = list.listIterator(3);
        it.previous();
        it.set("z");
        check("z".equals(list.get(2)), "set after previous() must replace last element");
        check("a".equals(list.get(0)), "first element must be unchanged");
    }

    static void testSetAcceptsNullElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        it.next();
        it.set(null);
        check(list.get(0) == null, "set(null) must store null");
    }

    static void testSetAfterRemoveThrowsIllegalState() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.set("x");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "set after remove() must throw IllegalStateException");
        check(list.size() == 1 && "b".equals(list.get(0)), "list state must match removal");
    }

    static void testSetAfterExternalModificationThrowsConcurrentModification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        it.next();
        list.add("c");
        boolean thrown = false;
        try {
            it.set("x");
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "set after external structural modification must throw ConcurrentModificationException");
    }
}

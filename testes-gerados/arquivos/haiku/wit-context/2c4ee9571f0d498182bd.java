/*
 * @test
 * @summary WITUP-derived tests for LinkedList ListItr.next() through the public listIterator() API
 * @run main LinkedListWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testNextOnEmptyListThrowsNoSuchElement();
        testNextAfterFullTraversalThrowsNoSuchElement();
        testNextDetectsStructuralModification();
        testIteratorRemoveDoesNotCauseComodification();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNextOnEmptyListThrowsNoSuchElement() {
        LinkedList<String> list = new LinkedList<>();
        ListIterator<String> it = list.listIterator();
        check(!it.hasNext(), "empty list iterator should have no next");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty list iterator must throw NoSuchElementException");
    }

    static void testNextAfterFullTraversalThrowsNoSuchElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        check("a".equals(it.next()), "first element mismatch");
        check("b".equals(it.next()), "second element mismatch");
        check(!it.hasNext(), "iterator should be exhausted");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() after exhaustion must throw NoSuchElementException");
    }

    static void testNextDetectsStructuralModification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        list.add("b");
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "next() after external structural modification must throw ConcurrentModificationException");
    }

    static void testIteratorRemoveDoesNotCauseComodification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        check("a".equals(it.next()), "first element mismatch");
        it.remove();
        check(list.size() == 1, "list size should be 1 after iterator remove");
        check("b".equals(it.next()), "next() after iterator remove should return b without CME");
    }
}

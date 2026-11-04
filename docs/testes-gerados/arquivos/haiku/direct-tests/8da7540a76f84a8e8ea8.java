/*
 * @test
 * @summary Verify ListIterator.previous() behavior of LinkedList through its public listIterator API
 * @run main LinkedListWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testPreviousFromEnd();
        testPreviousAfterNext();
        testPreviousIndexUpdates();
        testPreviousOnEmptyListThrows();
        testPreviousAtStartThrows();
        testPreviousReturnsNullElement();
        testPreviousAfterStructuralModificationThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPreviousFromEnd() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ListIterator<String> it = list.listIterator(3);
        check(it.hasPrevious(), "expected hasPrevious at end");
        check("c".equals(it.previous()), "first previous should be c");
        check("b".equals(it.previous()), "second previous should be b");
        check("a".equals(it.previous()), "third previous should be a");
        check(!it.hasPrevious(), "expected no previous at start");
    }

    static void testPreviousAfterNext() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ListIterator<String> it = list.listIterator(0);
        check("a".equals(it.next()), "next should return a");
        check("a".equals(it.previous()), "previous after next should return a");
        check(it.nextIndex() == 0, "nextIndex should be 0");
        check(it.previousIndex() == -1, "previousIndex should be -1");
    }

    static void testPreviousIndexUpdates() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        ListIterator<Integer> it = list.listIterator(3);
        check(it.previousIndex() == 2, "initial previousIndex should be 2");
        check(it.previous() == 30, "previous should return 30");
        check(it.previousIndex() == 1, "previousIndex should decrement to 1");
        check(it.nextIndex() == 2, "nextIndex should decrement to 2");
    }

    static void testPreviousOnEmptyListThrows() {
        LinkedList<String> list = new LinkedList<>();
        ListIterator<String> it = list.listIterator(0);
        try {
            it.previous();
            throw new RuntimeException("expected NoSuchElementException on empty list");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testPreviousAtStartThrows() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        ListIterator<String> it = list.listIterator(0);
        try {
            it.previous();
            throw new RuntimeException("expected NoSuchElementException at start");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testPreviousReturnsNullElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        list.add("b");
        ListIterator<String> it = list.listIterator(2);
        check("b".equals(it.previous()), "first previous should be b");
        check(it.previous() == null, "second previous should be null element");
    }

    static void testPreviousAfterStructuralModificationThrows() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator(2);
        list.add("c");
        try {
            it.previous();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}

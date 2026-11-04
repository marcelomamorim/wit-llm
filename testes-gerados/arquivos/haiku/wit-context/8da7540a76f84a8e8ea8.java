/*
 * @test
 * @summary Witup-derived checks for LinkedList ListItr.previous() exception and traversal behavior
 * @run main LinkedListWitupTest
 */
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testPreviousOnEmptyListThrowsNoSuchElement();
        testPreviousTraversesBackwardsThenThrows();
        testPreviousReturnsElementAddedByIterator();
        testPreviousDetectsComodification();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPreviousOnEmptyListThrowsNoSuchElement() {
        LinkedList<String> list = new LinkedList<>();
        ListIterator<String> it = list.listIterator(0);
        try {
            it.previous();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testPreviousTraversesBackwardsThenThrows() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ListIterator<String> it = list.listIterator(3);
        check(it.hasPrevious(), "iterator at end should have previous");
        check("c".equals(it.previous()), "first previous should be c");
        check("b".equals(it.previous()), "second previous should be b");
        check("a".equals(it.previous()), "third previous should be a");
        check(!it.hasPrevious(), "iterator at start should not have previous");
        try {
            it.previous();
            throw new RuntimeException("expected NoSuchElementException at start");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testPreviousReturnsElementAddedByIterator() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator(0);
        check("a".equals(it.next()), "next should be a");
        it.add("z");
        check("z".equals(it.previous()), "previous after iterator add should be z");
        check("a".equals(it.previous()), "previous after z should be a");
    }

    static void testPreviousDetectsComodification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator(1);
        list.add("c");
        try {
            it.previous();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}

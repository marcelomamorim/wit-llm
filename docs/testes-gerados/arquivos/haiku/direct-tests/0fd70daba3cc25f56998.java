/*
 * @test
 * @summary Verifies observable behavior of LinkedList iterator remove() (ListItr.remove) via public iterator APIs
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutNextThrowsIllegalState();
        testRemoveAfterNextRemovesReturnedElement();
        testSecondRemoveWithoutNextThrowsIllegalState();
        testRemoveAfterPreviousRemovesReturnedElement();
        testRemoveDetectsComodification();
        testRemoveAllEvenElements();
        testNextAfterRemoveContinuesCorrectly();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalState(Iterator<Integer> it) {
        try {
            it.remove();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException");
    }

    static void testRemoveWithoutNextThrowsIllegalState() {
        LinkedList<Integer> l = new LinkedList<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = l.iterator();
        expectIllegalState(it);
        check(l.equals(Arrays.asList(1, 2, 3)), "list changed after failed remove");
    }

    static void testRemoveAfterNextRemovesReturnedElement() {
        LinkedList<Integer> l = new LinkedList<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = l.iterator();
        int v = it.next();
        check(v == 1, "next() returned unexpected value");
        it.remove();
        check(l.equals(Arrays.asList(2, 3)), "remove() did not remove returned element");
    }

    static void testSecondRemoveWithoutNextThrowsIllegalState() {
        LinkedList<Integer> l = new LinkedList<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = l.iterator();
        it.next();
        it.remove();
        expectIllegalState(it);
        check(l.equals(Arrays.asList(2, 3)), "list changed after second remove");
    }

    static void testRemoveAfterPreviousRemovesReturnedElement() {
        LinkedList<Integer> l = new LinkedList<>(Arrays.asList(1, 2, 3));
        ListIterator<Integer> li = l.listIterator(l.size());
        int v = li.previous();
        check(v == 3, "previous() returned unexpected value");
        li.remove();
        check(l.equals(Arrays.asList(1, 2)), "remove() after previous() failed");
    }

    static void testRemoveDetectsComodification() {
        LinkedList<Integer> l = new LinkedList<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = l.iterator();
        it.next();
        l.add(4);
        try {
            it.remove();
        } catch (ConcurrentModificationException e) {
            return;
        }
        throw new RuntimeException("expected ConcurrentModificationException");
    }

    static void testRemoveAllEvenElements() {
        LinkedList<Integer> l = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Iterator<Integer> it = l.iterator();
        while (it.hasNext()) {
            int v = it.next();
            if (v % 2 == 0) {
                it.remove();
            }
        }
        check(l.equals(Arrays.asList(1, 3, 5)), "removing evens produced wrong list");
    }

    static void testNextAfterRemoveContinuesCorrectly() {
        LinkedList<Integer> l = new LinkedList<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = l.iterator();
        it.next();
        it.remove();
        int v = it.next();
        check(v == 2, "next() after remove() returned wrong element");
        it.remove();
        check(l.equals(Arrays.asList(3)), "remaining list incorrect");
    }
}

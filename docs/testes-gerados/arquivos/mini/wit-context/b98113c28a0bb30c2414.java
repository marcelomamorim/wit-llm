/*
 * @test
 * @summary Test LinkedList.next() method behavior including exception paths
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testNextReturnsPreviousElement();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextReturnsPreviousElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        // Create descending iterator
        LinkedList.DescendingIterator<String> descItr = new LinkedList.DescendingIterator<>(list);
        // next() calls itr.previous(), so next() should return last element first
        String first = descItr.next();
        if (!"b".equals(first)) {
            throw new AssertionError("Expected 'b' but got " + first);
        }
        String second = descItr.next();
        if (!"a".equals(second)) {
            throw new AssertionError("Expected 'a' but got " + second);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        LinkedList<String> list = new LinkedList<>();
        // empty list descending iterator
        LinkedList.DescendingIterator<String> descItr = new LinkedList.DescendingIterator<>(list);
        boolean thrown = false;
        try {
            descItr.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty descending iterator");
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        LinkedList.DescendingIterator<String> descItr = new LinkedList.DescendingIterator<>(list);
        // Modify list structurally to cause modCount mismatch
        list.add("y");
        boolean thrown = false;
        try {
            descItr.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ConcurrentModificationException due to concurrent modification");
        }
    }

    // Inner class to access LinkedList.DescendingIterator for testing
    // This is necessary because DescendingIterator is package-private in LinkedList
    // We replicate minimal DescendingIterator here for testing purposes
    static class DescendingIterator<E> {
        private final ListIterator<E> itr;

        DescendingIterator(LinkedList<E> list) {
            this.itr = list.listIterator(list.size());
        }

        public E next() {
            return itr.previous();
        }
    }
}

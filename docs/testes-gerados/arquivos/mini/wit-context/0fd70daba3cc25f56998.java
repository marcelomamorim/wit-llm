/*
 * @test
 * @summary Test LinkedList.ListItr.remove() for expected exceptions and state changes
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.lang.IllegalStateException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextThrowsIllegalStateException();
        testRemoveAfterConcurrentModificationThrowsConcurrentModificationException();
        testRemoveUpdatesStateCorrectly();
    }

    // Test that calling remove() before next() or previous() throws IllegalStateException
    private static void testRemoveWithoutNextThrowsIllegalStateException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    // Test that remove() throws ConcurrentModificationException if list structurally modified outside iterator
    private static void testRemoveAfterConcurrentModificationThrowsConcurrentModificationException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        it.next(); // move to first element
        // Structural modification outside iterator
        list.add("c");
        try {
            it.remove();
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    // Test that remove() removes last returned element and updates iterator state
    private static void testRemoveUpdatesStateCorrectly() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ListIterator<String> it = list.listIterator();

        // Move to first element and remove it
        String first = it.next();
        if (!"a".equals(first)) {
            throw new RuntimeException("Unexpected first element: " + first);
        }
        it.remove();
        if (list.size() != 2 || list.contains("a")) {
            throw new RuntimeException("Element not removed correctly");
        }

        // After removal, next should be "b"
        String next = it.next();
        if (!"b".equals(next)) {
            throw new RuntimeException("Unexpected next element after removal: " + next);
        }

        // Remove second element
        it.remove();
        if (list.size() != 1 || list.contains("b")) {
            throw new RuntimeException("Second element not removed correctly");
        }

        // Next element should be "c"
        next = it.next();
        if (!"c".equals(next)) {
            throw new RuntimeException("Unexpected next element after second removal: " + next);
        }

        // Remove last element
        it.remove();
        if (!list.isEmpty()) {
            throw new RuntimeException("List should be empty after removing all elements");
        }

        // Now remove() should throw IllegalStateException again
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException after remove without next");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}

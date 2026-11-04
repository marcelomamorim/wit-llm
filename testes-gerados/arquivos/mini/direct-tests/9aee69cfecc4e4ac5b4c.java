/*
 * @test
 * @summary Test java.util.ArrayList remove() method behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.IllegalStateException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextThrowsIllegalStateException();
        testRemoveAfterNextRemovesElement();
        testRemoveAfterRemoveThrowsIllegalStateException();
        testRemoveConcurrentModificationException();
    }

    // Test that calling remove() before next() throws IllegalStateException
    private static void testRemoveWithoutNextThrowsIllegalStateException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        Iterator<String> it = list.iterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    // Test that remove() after next() removes the element and updates iterator state
    private static void testRemoveAfterNextRemovesElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Iterator<String> it = list.iterator();
        String first = it.next();
        if (!"a".equals(first)) {
            throw new RuntimeException("Expected first element 'a', got " + first);
        }
        it.remove();
        if (list.size() != 1 || !"b".equals(list.get(0))) {
            throw new RuntimeException("Element not removed correctly");
        }
        // After remove, calling remove again without next should throw
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException on second remove call");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    // Test that calling remove() twice without next() throws IllegalStateException
    private static void testRemoveAfterRemoveThrowsIllegalStateException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        Iterator<String> it = list.iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException on second remove call");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    // Test that concurrent modification during remove() throws ConcurrentModificationException
    private static void testRemoveConcurrentModificationException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Iterator<String> it = list.iterator();
        it.next();
        // Modify list structurally outside iterator
        list.add("c");
        try {
            it.remove();
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Test TreeMap remove() method behavior including exceptions and state changes
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNext();
        testRemoveAfterConcurrentModification();
        testRemoveValid();
    }

    // Test that calling remove() before next() throws IllegalStateException
    private static void testRemoveWithoutNext() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        Iterator<Integer> it = map.keySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when calling remove() before next()");
        }
    }

    // Test that remove() throws ConcurrentModificationException if map modified externally
    private static void testRemoveAfterConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        Iterator<Integer> it = map.keySet().iterator();
        it.next();
        map.put(2, "two"); // structural modification
        boolean thrown = false;
        try {
            it.remove();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ConcurrentModificationException when calling remove() after concurrent modification");
        }
    }

    // Test valid remove() call after next() removes the element and iterator behaves correctly
    private static void testRemoveValid() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        Iterator<Integer> it = map.keySet().iterator();
        Integer first = it.next();
        it.remove();
        if (map.containsKey(first)) {
            throw new RuntimeException("Element was not removed from map after iterator.remove()");
        }
        // After removal, next() should return the next element
        Integer second = it.next();
        if (!second.equals(2)) {
            throw new RuntimeException("Iterator did not return expected next element after removal");
        }
        // Remove second element
        it.remove();
        if (map.size() != 0) {
            throw new RuntimeException("Map should be empty after removing all elements via iterator");
        }
        // next() now should throw NoSuchElementException
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NoSuchElementException when calling next() on exhausted iterator");
        }
    }
}

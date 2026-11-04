/*
 * @test
 * @summary Test TreeMap remove() method behavior for expected exceptions and state changes
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.lang.IllegalStateException;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextSucceeds();
        testRemoveAfterConcurrentModificationThrows();
    }

    // Test that calling remove() before next() throws IllegalStateException
    private static void testRemoveWithoutNextThrows() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        Iterator<Integer> it = map.keySet().iterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    // Test that remove() after next() removes the element and iterator behaves correctly
    private static void testRemoveAfterNextSucceeds() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        Iterator<Integer> it = map.keySet().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator has no next element");
        }
        Integer first = it.next();
        it.remove();
        if (map.containsKey(first)) {
            throw new RuntimeException("Element was not removed from map");
        }
        // Removing again without next() should throw
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException on second remove not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    // Test that remove() throws ConcurrentModificationException if map modified concurrently
    private static void testRemoveAfterConcurrentModificationThrows() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        Iterator<Integer> it = map.keySet().iterator();
        it.next();
        // Modify map structurally outside iterator
        map.put(3, "three");
        try {
            it.remove();
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}

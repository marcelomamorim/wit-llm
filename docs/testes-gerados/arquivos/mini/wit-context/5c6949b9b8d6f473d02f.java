/*
 * @test
 * @summary Test java.util.TreeMap.DescendingKeyIterator.remove() exception paths
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutLastReturned();
        testRemoveWithConcurrentModification();
    }

    // Test that remove() throws IllegalStateException if lastReturned == null
    private static void testRemoveWithoutLastReturned() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        Iterator<Integer> it = map.descendingKeySet().iterator();

        // Immediately call remove() without next() to cause lastReturned == null
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected
        }
    }

    // Test that remove() throws ConcurrentModificationException if modCount != expectedModCount
    private static void testRemoveWithConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        Iterator<Integer> it = map.descendingKeySet().iterator();

        // Advance iterator to have lastReturned set
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator has no elements");
        }
        it.next();

        // Modify map structurally outside iterator
        map.put(3, "three");

        try {
            it.remove();
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected
        }
    }
}

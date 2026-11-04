/*
 * @test
 * @summary Test TreeMap.NavigableSubMap.DescendingSubMapKeyIterator.remove() exceptional behavior
 * @run main java.util.TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.NavigableMap;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Map;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutLastReturned();
        testRemoveConcurrentModification();
    }

    // Test that remove() throws IllegalStateException if lastReturned == null
    private static void testRemoveWithoutLastReturned() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        NavigableMap<Integer, String> descendingMap = map.descendingMap();
        Iterator<Integer> it = descendingMap.keySet().iterator();

        // Immediately call remove() without next() to cause IllegalStateException
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // Expected
        }
    }

    // Test that remove() throws ConcurrentModificationException if map modified concurrently
    private static void testRemoveConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        NavigableMap<Integer, String> descendingMap = map.descendingMap();
        Iterator<Integer> it = descendingMap.keySet().iterator();

        // Advance iterator once
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator unexpectedly empty");
        }
        Integer first = it.next();

        // Modify map structurally outside iterator
        map.put(4, "four");

        // Now remove() should throw ConcurrentModificationException
        try {
            it.remove();
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException cme) {
            // Expected
        }
    }
}

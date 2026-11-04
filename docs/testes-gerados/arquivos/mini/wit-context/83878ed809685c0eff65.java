/*
 * @test
 * @summary Test TreeMap.NavigableSubMap.SubMapKeyIterator.remove() exceptional paths
 * @run main java.util.TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.SortedMap;
import java.util.NavigableMap;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutLastReturned();
        testRemoveWithConcurrentModification();
    }

    // Test that calling remove() without a prior next() call throws IllegalStateException
    private static void testRemoveWithoutLastReturned() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        // Get a subMap to access NavigableSubMap and its iterator
        SortedMap<Integer, String> subMap = map.subMap(1, 3);
        Iterator<Integer> it = subMap.keySet().iterator();

        boolean illegalStateThrown = false;
        try {
            it.remove(); // remove() called before next(), should throw IllegalStateException
        } catch (IllegalStateException e) {
            illegalStateThrown = true;
        }

        if (!illegalStateThrown) {
            throw new AssertionError("Expected IllegalStateException when calling remove() before next()");
        }
    }

    // Test that remove() throws ConcurrentModificationException if map is structurally modified
    private static void testRemoveWithConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        SortedMap<Integer, String> subMap = map.subMap(1, 3);
        Iterator<Integer> it = subMap.keySet().iterator();

        // Advance iterator once to set lastReturned
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        it.next();

        // Structural modification outside iterator
        map.put(3, "three");

        boolean concurrentModThrown = false;
        try {
            it.remove(); // should throw ConcurrentModificationException due to modCount mismatch
        } catch (ConcurrentModificationException e) {
            concurrentModThrown = true;
        }

        if (!concurrentModThrown) {
            throw new AssertionError("Expected ConcurrentModificationException when calling remove() after concurrent modification");
        }
    }
}

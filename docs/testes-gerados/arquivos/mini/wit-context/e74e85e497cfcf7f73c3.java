/*
 * @test
 * @summary Test TreeMap.NavigableSubMap.SubMapEntryIterator.remove() exception paths
 * @run main java.util.TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.SortedMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutLastReturned();
        testRemoveConcurrentModification();
    }

    // Test that calling remove() without a prior next() call throws IllegalStateException
    private static void testRemoveWithoutLastReturned() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        SortedMap<Integer, String> subMap = map.subMap(1, 2);
        Iterator<Map.Entry<Integer, String>> it = subMap.entrySet().iterator();

        boolean caught = false;
        try {
            it.remove(); // remove called before next()
        } catch (IllegalStateException e) {
            caught = true;
        }
        if (!caught) {
            throw new RuntimeException("Expected IllegalStateException when remove() called before next()");
        }
    }

    // Test that remove() throws ConcurrentModificationException if map is modified concurrently
    private static void testRemoveConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        SortedMap<Integer, String> subMap = map.subMap(1, 3);
        Iterator<Map.Entry<Integer, String>> it = subMap.entrySet().iterator();

        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have elements");
        }
        it.next(); // advance iterator

        // Modify the underlying map structurally
        map.put(3, "three");

        boolean caught = false;
        try {
            it.remove(); // should throw ConcurrentModificationException
        } catch (ConcurrentModificationException e) {
            caught = true;
        }
        if (!caught) {
            throw new RuntimeException("Expected ConcurrentModificationException when remove() called after concurrent modification");
        }
    }
}

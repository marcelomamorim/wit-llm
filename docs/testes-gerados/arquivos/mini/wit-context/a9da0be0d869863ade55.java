/*
 * @test
 * @summary Deterministic tests for java.util.TreeMap remove() method in DescendingSubMapEntryIterator
 * @run main java.util.TreeMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.TreeMap;
import java.util.NavigableMap;
import java.util.Iterator;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutLastReturned();
        testRemoveWithConcurrentModification();
        System.out.println("All tests passed.");
    }

    // Test for IllegalStateException when remove() called without lastReturned
    private static void testRemoveWithoutLastReturned() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        NavigableMap<Integer, String> descendingMap = map.descendingMap();
        Iterator<Integer> it = descendingMap.keySet().iterator();

        // Do not call next(), so lastReturned is null
        try {
            it.remove();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // Expected
        }
    }

    // Test for ConcurrentModificationException when map modified after iterator creation
    private static void testRemoveWithConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        NavigableMap<Integer, String> descendingMap = map.descendingMap();
        Iterator<Integer> it = descendingMap.keySet().iterator();

        // Advance iterator to have lastReturned
        if (!it.hasNext()) {
            throw new AssertionError("Iterator has no elements");
        }
        it.next();

        // Modify map structurally
        map.put(4, "four");

        try {
            it.remove();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException cme) {
            // Expected
        }
    }
}

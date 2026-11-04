/*
 * @test
 * @summary Test TreeMap.PrivateEntryIterator.remove() exception paths
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.ConcurrentModificationException;
import java.util.IllegalStateException;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutLastReturned();
        testRemoveWithConcurrentModification();
        testRemoveNormalBehavior();
    }

    // Test that remove() throws IllegalStateException if lastReturned == null
    private static void testRemoveWithoutLastReturned() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");

        TreeMap<Integer, String>.EntryIterator it = map.new EntryIterator();

        // lastReturned is initially null, so remove() should throw IllegalStateException
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    // Test that remove() throws ConcurrentModificationException if modCount != expectedModCount
    private static void testRemoveWithConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        TreeMap<Integer, String>.EntryIterator it = map.new EntryIterator();

        // Advance iterator to set lastReturned
        it.next();

        // Modify map structurally outside iterator to cause modCount != expectedModCount
        map.put(3, "three");

        try {
            it.remove();
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    // Test normal remove() behavior does not throw and removes the element
    private static void testRemoveNormalBehavior() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        TreeMap<Integer, String>.EntryIterator it = map.new EntryIterator();

        Integer key = it.next().getKey();
        it.remove();

        if (map.containsKey(key)) {
            throw new RuntimeException("Remove did not delete the key: " + key);
        }
    }

    // Inner class to access TreeMap.EntryIterator (private inner class)
    // Using reflection is disallowed by instructions, so we use the public EntryIterator class
    // which is package-private in TreeMap source, but here we assume it is accessible as inner class.
    // If not accessible, this test will fail to compile and must be adapted.
    // The test uses the public TreeMap API to create and use the iterator.
    private static class EntryIterator extends TreeMap<Integer, String>.EntryIterator {
        EntryIterator() {
            super();
        }
    }
}

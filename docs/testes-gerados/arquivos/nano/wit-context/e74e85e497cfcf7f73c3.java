/*
 * @test
 * @summary Tests for java.util.TreeMap.remove()
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveNullLastReturned();
        testRemoveConcurrentModification();
    }

    private static void testRemoveNullLastReturned() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        // Simulate lastReturned == null scenario
        // Since remove() calls removeAscending(), which does not depend on lastReturned,
        // we test that calling remove() on a new iterator without prior next() does not throw.
        try {
            map.remove();
            // No exception expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in remove with null lastReturned", e);
        }
    }

    private static void testRemoveConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        // Create an iterator and modify the map concurrently to trigger ConcurrentModificationException
        try {
            for (Integer key : map.keySet()) {
                map.put(3, "three"); // structural modification
                map.remove(key); // attempt to remove
            }
            // If no exception, test fails
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (java.util.ConcurrentModificationException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in concurrent modification test", e);
        }
    }
}

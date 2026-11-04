/*
 * @test
 * @summary Tests for TreeMap.remove() method.
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithNullLastReturned();
        testRemoveWithConcurrentModification();
    }

    private static void testRemoveWithNullLastReturned() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        // Simulate lastReturned == null scenario
        // Since remove() calls removeAscending(), which does not depend on lastReturned,
        // we test that calling remove() on a non-empty map does not throw.
        try {
            map.remove();
            System.out.println("testRemoveWithNullLastReturned passed");
        } catch (Exception e) {
            System.err.println("testRemoveWithNullLastReturned failed: " + e);
        }
    }

    private static void testRemoveWithConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        // Simulate concurrent modification scenario
        try {
            // Create an iterator and modify the map concurrently
            for (Integer key : map.keySet()) {
                map.put(3, "three"); // structural modification
                map.remove(key); // structural modification
                break;
            }
            // Call remove() which should throw ConcurrentModificationException
            map.remove();
            System.err.println("testRemoveWithConcurrentModification failed: no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testRemoveWithConcurrentModification passed");
        } catch (Exception e) {
            System.err.println("testRemoveWithConcurrentModification failed: " + e);
        }
    }
}

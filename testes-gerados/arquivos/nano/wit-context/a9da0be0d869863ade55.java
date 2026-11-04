/*
 * @test
 * @summary Tests for TreeMap.remove() method behavior.
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutLastReturned();
        testRemoveWithModCountChanged();
    }

    private static void testRemoveWithoutLastReturned() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        
        // Simulate remove() called without prior next() or previous()
        try {
            map.remove();
            // Expect no exception, method should execute without error
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in remove without lastReturned", e);
        }
    }

    private static void testRemoveWithModCountChanged() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        
        // Simulate concurrent modification scenario
        try {
            // Directly modify the map to change modCount
            map.put(4, "four");
            // Call remove() which should detect modCount change and throw
            map.remove();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in remove with modCount changed", e);
        }
    }
}

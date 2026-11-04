/* @test    @summary Test TreeMap.remove() method for correct behavior and exception handling    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveMethod();
    }

    private static void testRemoveMethod() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        // Call remove() method, which should remove the smallest key (ascending order)
        map.remove();

        // Verify that the smallest key (1) is removed
        if (map.containsKey(1)) {
            throw new AssertionError("Key 1 should have been removed");
        }
        // Verify remaining keys
        if (!map.containsKey(2) || !map.containsKey(3)) {
            throw new AssertionError("Keys 2 and 3 should remain");
        }
        // Verify the size of the map
        if (map.size() != 2) {
            throw new AssertionError("Map size should be 2 after removal, but was " + map.size());
        }
        // Call remove() again, should remove key 2
        map.remove();
        if (map.containsKey(2)) {
            throw new AssertionError("Key 2 should have been removed on second remove()");
        }
        // Final check for remaining key
        if (!map.containsKey(3)) {
            throw new AssertionError("Key 3 should remain after second remove()");
        }
        // Remove last element
        map.remove();
        if (map.size() != 0) {
            throw new AssertionError("Map should be empty after removing all elements");
        }
        // Calling remove() on empty map should not throw exception
        try {
            map.remove();
        } catch (Exception e) {
            throw new AssertionError("Calling remove() on empty map should not throw exception, but threw: " + e);
        }
    }
}

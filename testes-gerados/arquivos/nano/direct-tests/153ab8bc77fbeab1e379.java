/* @test    @summary Test TreeMap.remove() method for existing and non-existing keys    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveExistingKey();
        testRemoveNonExistingKey();
    }

    private static void testRemoveExistingKey() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        String removedValue = map.remove(1);
        if (!"one".equals(removedValue)) {
            throw new AssertionError("Expected removed value to be 'one', but was: " + removedValue);
        }
        if (map.containsKey(1)) {
            throw new AssertionError("Map should not contain key 1 after removal");
        }
        if (map.size() != 1) {
            throw new AssertionError("Map size should be 1 after removal, but was: " + map.size());
        }
    }

    private static void testRemoveNonExistingKey() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(3, "three");
        String result = map.remove(4);
        if (result != null) {
            throw new AssertionError("Removing non-existing key should return null, but got: " + result);
        }
        if (map.size() != 1) {
            throw new AssertionError("Map size should remain 1 after attempting to remove non-existing key, but was: " + map.size());
        }
        if (!map.containsKey(3)) {
            throw new AssertionError("Map should still contain key 3");
        }
    }
}

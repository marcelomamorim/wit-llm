/*
 * @test
 * @summary Test for TreeMap.remove(Object key)
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveExistingKey();
        testRemoveNonExistingKey();
        testRemoveNullKey();
    }

    private static void testRemoveExistingKey() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Integer removed = map.remove("a");
        if (removed == null || !map.containsKey("a")) {
            throw new AssertionError("Failed to remove existing key 'a'");
        }
        if (removed != 1) {
            throw new AssertionError("Removed value mismatch for key 'a'");
        }
    }

    private static void testRemoveNonExistingKey() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("x", 10);
        Integer result = map.remove("y");
        if (result != null) {
            throw new AssertionError("Expected null when removing non-existing key 'y'");
        }
        if (!map.containsKey("x")) {
            throw new AssertionError("Map should still contain key 'x' after removing non-existing key")
        }
    }

    private static void testRemoveNullKey() {
        TreeMap<String, Integer> map = new TreeMap<>();
        try {
            map.remove(null);
            throw new AssertionError("Expected NullPointerException when removing null key");
        } catch (NullPointerException e) {
            // expected
        }
    }
}

/* @test    @summary Test HashMap constructor with initial capacity    @run main HashMapWitupTest */

import java.util.HashMap;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testHashMapWithInitialCapacity();
    }

    public static void testHashMapWithInitialCapacity() {
        int initialCapacity = 10;
        HashMap<String, Integer> map = new HashMap<>(initialCapacity);
        if (map == null) {
            throw new AssertionError("HashMap instance is null");
        }
        // Verify that the map is empty after construction
        if (map.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + map.size());
        }
        // Verify that the map does not contain any keys
        if (!map.isEmpty()) {
            throw new AssertionError("Map should be empty after construction");
        }
        // Insert an element and verify size
        map.put("key", 1);
        if (map.size() != 1) {
            throw new AssertionError("Expected size 1 after insertion, but got " + map.size());
        }
        // Verify that the inserted key exists
        if (!map.containsKey("key")) {
            throw new AssertionError("Map should contain the key "key"");
        }
        // Verify that the value is correct
        if (map.get("key") != 1) {
            throw new AssertionError("Value for 'key' should be 1, but got " + map.get("key"));
        }
    }
}

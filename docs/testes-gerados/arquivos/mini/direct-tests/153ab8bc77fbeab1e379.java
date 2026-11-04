/*
 * @test
 * @summary Test TreeMap.remove(Object) behavior
 * @run main java.util.TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testRemoveExistingKey();
        testRemoveNonExistingKey();
        testRemoveNullKey();
    }

    private static void testRemoveExistingKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        String removed = map.remove("key1");
        if (!"value1".equals(removed)) {
            throw new RuntimeException("Failed to remove existing key: expected value1, got " + removed);
        }
        if (map.containsKey("key1")) {
            throw new RuntimeException("Key 'key1' still present after removal");
        }
    }

    private static void testRemoveNonExistingKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("key1", "value1");

        String removed = map.remove("nonexistent");
        if (removed != null) {
            throw new RuntimeException("Removing non-existing key should return null, got " + removed);
        }
        if (!map.containsKey("key1")) {
            throw new RuntimeException("Existing key 'key1' missing after removing non-existing key");
        }
    }

    private static void testRemoveNullKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put(null, "nullValue");

        String removed = map.remove(null);
        if (!"nullValue".equals(removed)) {
            throw new RuntimeException("Failed to remove null key: expected nullValue, got " + removed);
        }
        if (map.containsKey(null)) {
            throw new RuntimeException("Null key still present after removal");
        }
    }
}

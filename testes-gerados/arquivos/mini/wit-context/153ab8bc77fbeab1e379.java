/*
 * @test
 * @summary Deterministic tests for java.util.TreeMap.remove(Object) method
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
        TreeMap<String, String> map = new TreeMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        String removed = map.remove("key1");
        if (!"value1".equals(removed)) {
            throw new RuntimeException("Failed: remove should return the value of the removed key");
        }
        if (map.containsKey("key1")) {
            throw new RuntimeException("Failed: key1 should no longer be in the map after removal");
        }
    }

    private static void testRemoveNonExistingKey() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(1, 10);
        map.put(2, 20);

        Integer removed = map.remove(3);
        if (removed != null) {
            throw new RuntimeException("Failed: remove of non-existing key should return null");
        }
        if (map.size() != 2) {
            throw new RuntimeException("Failed: map size should remain unchanged after removing non-existing key");
        }
    }

    private static void testRemoveNullKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "A");

        try {
            map.remove(null);
            throw new RuntimeException("Failed: remove(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}

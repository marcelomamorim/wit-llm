/*
 * @test
 * @summary Test remove method of ConcurrentSkipListMap for key removal behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testRemoveExistingKey();
        testRemoveNonExistingKey();
        testRemoveNullKey();
    }

    private static void testRemoveExistingKey() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        Integer removedValue = map.remove("a");
        if (removedValue == null || !removedValue.equals(1)) {
            throw new AssertionError("Failed to remove existing key 'a' or incorrect value returned");
        }
        if (map.containsKey("a")) {
            throw new AssertionError("Key 'a' should no longer be in the map after removal");
        }
    }

    private static void testRemoveNonExistingKey() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("b", 2);
        Integer result = map.remove("nonexistent");
        if (result != null) {
            throw new AssertionError("Removing non-existing key should return null");
        }
        if (!map.containsKey("b")) {
            throw new AssertionError("Existing key 'b' should remain in the map after attempting to remove non-existent key");
        }
    }

    private static void testRemoveNullKey() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("c", 3);
        try {
            map.remove(null);
            throw new AssertionError("Removing null key should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected exception
        }
        if (!map.containsKey("c")) {
            throw new AssertionError("Key 'c' should remain in the map after attempting to remove null key");
        }
    }
}

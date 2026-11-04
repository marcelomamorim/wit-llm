/*
 * @test
 * @summary Test ConcurrentSkipListMap.remove(Object) for null key and normal removal
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testRemoveNullKeyThrowsNPE();
        testRemoveExistingKeyReturnsValue();
        testRemoveNonExistingKeyReturnsNull();
    }

    private static void testRemoveNullKeyThrowsNPE() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean npeThrown = false;
        try {
            map.remove(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when removing null key");
        }
    }

    private static void testRemoveExistingKeyReturnsValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        String removed = map.remove("key1");
        if (!"value1".equals(removed)) {
            throw new AssertionError("Expected removed value to be 'value1', got: " + removed);
        }
        if (map.containsKey("key1")) {
            throw new AssertionError("Key 'key1' should no longer be present after removal");
        }
    }

    private static void testRemoveNonExistingKeyReturnsNull() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        String removed = map.remove("key2");
        if (removed != null) {
            throw new AssertionError("Expected null when removing non-existing key, got: " + removed);
        }
        if (!map.containsKey("key1")) {
            throw new AssertionError("Existing key 'key1' should remain after removing non-existing key");
        }
    }
}

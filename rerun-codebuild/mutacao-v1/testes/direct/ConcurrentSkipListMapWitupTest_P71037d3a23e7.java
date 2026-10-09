/*
 * @test    @summary Test ConcurrentSkipListMap.remove(Object, Object) method for correct behavior
 * @run main ConcurrentSkipListMapWitupTest_P71037d3a23e7
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P71037d3a23e7 {

    public static void main(String[] args) {
        testRemoveWithExistingKeyAndValue();
        testRemoveWithExistingKeyAndDifferentValue();
        testRemoveWithNullKey();
        testRemoveWithNullValue();
        testRemoveWithNonExistingKey();
    }

    static void testRemoveWithExistingKeyAndValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        boolean result = map.remove("key1", "value1");
        if (!result) {
            throw new AssertionError("Expected true when removing existing key with matching value");
        }
        if (map.containsKey("key1")) {
            throw new AssertionError("Map should not contain 'key1' after removal");
        }
    }

    static void testRemoveWithExistingKeyAndDifferentValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key2", "value2");
        boolean result = map.remove("key2", "differentValue");
        if (result) {
            throw new AssertionError("Expected false when removing with non-matching value");
        }
        if (!map.containsKey("key2")) {
            throw new AssertionError("Map should still contain 'key2' after failed removal");
        }
    }

    static void testRemoveWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean threw = false;
        try {
            map.remove(null, "value");
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when removing with null key");
        }
    }

    static void testRemoveWithNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key3", "value3");
        boolean result = false;
        try {
            result = map.remove("key3", null);
        } catch (NullPointerException e) {
            throw new AssertionError("Did not expect NullPointerException when value is null");
        }
        if (result) {
            throw new AssertionError("Expected false when removing with null value");
        }
        if (!map.containsKey("key3")) {
            throw new AssertionError("Map should still contain 'key3' after failed removal");
        }
    }

    static void testRemoveWithNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean result = map.remove("nonexistent", "value");
        if (result) {
            throw new AssertionError("Expected false when removing non-existing key");
        }
    }
}

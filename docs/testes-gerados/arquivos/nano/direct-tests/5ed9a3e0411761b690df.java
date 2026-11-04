/*
 * @test
 * @summary Tests the remove(Object key, Object value) method of ConcurrentSkipListMap.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithExistingKeyAndValue();
        testRemoveWithExistingKeyAndNullValue();
        testRemoveWithNullKey();
        testRemoveWithNonExistingKey();
    }

    static void testRemoveWithExistingKeyAndValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key1";
        String value = "value1";
        map.put(key, value);
        boolean result = map.remove(key, value);
        assert result : "Expected remove to return true for existing key and value";
        assert !map.containsKey(key) : "Map should not contain key after removal";
    }

    static void testRemoveWithExistingKeyAndNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key2";
        String value = "value2";
        map.put(key, value);
        boolean result = map.remove(key, null);
        assert !result : "Expected remove to return false when value is null";
        assert map.containsKey(key) : "Map should still contain key when removal with null value fails";
    }

    static void testRemoveWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.remove(null, "value");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when removing with null key";
    }

    static void testRemoveWithNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean result = map.remove("nonexistent", "value");
        assert !result : "Expected remove to return false for non-existing key";
    }
}

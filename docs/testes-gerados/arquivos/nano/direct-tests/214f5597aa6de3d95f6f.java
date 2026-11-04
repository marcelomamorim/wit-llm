/*
 * @test    @summary Test ConcurrentHashMap.remove(Object, Object) for correctness and regressions
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithExistingKeyAndMatchingValue();
        testRemoveWithExistingKeyAndNonMatchingValue();
        testRemoveWithNullKey();
        testRemoveWithNullValue();
        testRemoveWithNonExistingKey();
    }

    static void testRemoveWithExistingKeyAndMatchingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String key = "key1";
        String value = "value1";
        map.put(key, value);
        boolean result = map.remove(key, value);
        assert result : "Expected removal to succeed for matching key and value";
        assert !map.containsKey(key) : "Map should not contain key after removal";
    }

    static void testRemoveWithExistingKeyAndNonMatchingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String key = "key2";
        String value = "value2";
        map.put(key, value);
        boolean result = map.remove(key, "differentValue");
        assert !result : "Expected removal to fail for non-matching value";
        assert map.containsKey(key) : "Map should still contain key after failed removal";
        assert map.get(key).equals(value) : "Value should remain unchanged";
    }

    static void testRemoveWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.remove(null, "value");
            assert false : "Expected NullPointerException for null key";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testRemoveWithNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String key = "key3";
        map.put(key, "value3");
        boolean result = map.remove(key, null);
        assert !result : "Removal should fail when value is null";
        assert map.containsKey(key) : "Map should still contain key";
        assert map.get(key).equals("value3") : "Value should remain unchanged";
    }

    static void testRemoveWithNonExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean result = map.remove("nonExistingKey", "value");
        assert !result : "Removal should fail for non-existing key";
    }
}

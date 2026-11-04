/*
 * @test
 * @summary Test ConcurrentHashMap.replace(K key, V value) for null checks and normal replacement
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testReplaceNullKey();
        testReplaceNullValue();
        testReplaceExistingKey();
        testReplaceNonExistingKey();
    }

    private static void testReplaceNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace(null, "value");
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace("key", null);
            throw new Error("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "oldValue");
        String old = map.replace("key", "newValue");
        if (!"oldValue".equals(old)) {
            throw new Error("replace should return old value when key exists");
        }
        String current = map.get("key");
        if (!"newValue".equals(current)) {
            throw new Error("replace should update value for existing key");
        }
    }

    private static void testReplaceNonExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String old = map.replace("missingKey", "value");
        if (old != null) {
            throw new Error("replace should return null when key does not exist");
        }
        if (map.containsKey("missingKey")) {
            throw new Error("replace should not add new key if absent");
        }
    }
}

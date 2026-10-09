/* @test    @summary Test the replace method of ConcurrentHashMap for various scenarios    @run main ConcurrentHashMapWitupTest_P46cf9c7d609a */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_P46cf9c7d609a {

    public static void main(String[] args) {
        testReplaceWithExistingKeyAndMatchingOldValue();
        testReplaceWithExistingKeyAndNonMatchingOldValue();
        testReplaceWithNullKey();
        testReplaceWithNullOldValue();
        testReplaceWithNullNewValue();
        testReplaceWithNonExistingKey();
    }

    static void testReplaceWithExistingKeyAndMatchingOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = map.replace("key1", "value1", "value2");
        if (!result) {
            throw new AssertionError("Expected replace to succeed for existing key with matching old value");
        }
        String newVal = map.get("key1");
        if (!"value2".equals(newVal)) {
            throw new AssertionError("Expected value to be 'value2', but was: " + newVal);
        }
    }

    static void testReplaceWithExistingKeyAndNonMatchingOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key2", "valueA");
        boolean result = map.replace("key2", "valueB", "valueC");
        if (result) {
            throw new AssertionError("Expected replace to fail for existing key with non-matching old value");
        }
        String val = map.get("key2");
        if (!"valueA".equals(val)) {
            throw new AssertionError("Expected value to remain 'valueA', but was: " + val);
        }
    }

    static void testReplaceWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace(null, "old", "new");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }
    }

    static void testReplaceWithNullOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key3", "value3");
        try {
            map.replace("key3", null, "newValue");
            throw new AssertionError("Expected NullPointerException for null oldValue");
        } catch (NullPointerException expected) {
        }
    }

    static void testReplaceWithNullNewValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key4", "value4");
        try {
            map.replace("key4", "value4", null);
            throw new AssertionError("Expected NullPointerException for null newValue");
        } catch (NullPointerException expected) {
        }
    }

    static void testReplaceWithNonExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean result = map.replace("nonexistent", "old", "new");
        if (result) {
            throw new AssertionError("Expected replace to fail for non-existing key");
        }
        if (map.containsKey("nonexistent")) {
            throw new AssertionError("Map should not contain the key after replace attempt");
        }
    }
}

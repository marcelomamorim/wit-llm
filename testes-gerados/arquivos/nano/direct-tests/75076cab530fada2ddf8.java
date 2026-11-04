/*
 * @test    @summary Test replace method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testReplaceExistingKey();
        testReplaceNonExistingKey();
        testReplaceWithNullKey();
        testReplaceWithNullValues();
    }

    static void testReplaceExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key1";
        String oldValue = "value1";
        String newValue = "value2";
        map.put(key, oldValue);
        boolean replaced = map.replace(key, oldValue, newValue);
        if (!replaced) {
            throw new AssertionError("Expected replace to return true for existing key with matching oldValue");
        }
        String result = map.get(key);
        if (!"value2".equals(result)) {
            throw new AssertionError("Expected value to be updated to 'value2', but got: " + result);
        }
    }

    static void testReplaceNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "nonexistent";
        String oldValue = "value";
        String newValue = "newValue";
        boolean replaced = map.replace(key, oldValue, newValue);
        if (replaced) {
            throw new AssertionError("Expected replace to return false for non-existing key");
        }
    }

    static void testReplaceWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replace(null, "old", "new");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReplaceWithNullValues() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key";
        map.put(key, "value");
        boolean replaced = false;
        try {
            replaced = map.replace(key, null, "newValue");
        } catch (NullPointerException e) {
            // depending on implementation, may throw
        }
        if (replaced) {
            String val = map.get(key);
            if (val != null && !"newValue".equals(val)) {
                throw new AssertionError("Value not replaced correctly when oldValue is null");
            }
        }
    }
}

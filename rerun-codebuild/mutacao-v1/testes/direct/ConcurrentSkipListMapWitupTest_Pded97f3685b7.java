/*
 * @test    @summary Test replace method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_Pded97f3685b7
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Pded97f3685b7 {

    public static void main(String[] args) {
        testReplaceSuccess();
        testReplaceFailOldValue();
        testReplaceNullArguments();
        testReplaceNodeNotFound();
    }

    static void testReplaceSuccess() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key1";
        String initialValue = "value1";
        String newValue = "value2";
        map.put(key, initialValue);
        boolean replaced = map.replace(key, initialValue, newValue);
        if (!replaced) {
            throw new AssertionError("Expected replace to succeed");
        }
        String result = map.get(key);
        if (!"value2".equals(result)) {
            throw new AssertionError("Expected value to be updated to 'value2', but got: " + result);
        }
    }

    static void testReplaceFailOldValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key2";
        map.put(key, "initial");
        boolean replaced = map.replace(key, "wrongOldValue", "newValue");
        if (replaced) {
            throw new AssertionError("Expected replace to fail due to wrong old value");
        }
        String result = map.get(key);
        if (!"initial".equals(result)) {
            throw new AssertionError("Value should remain unchanged, but got: " + result);
        }
    }

    static void testReplaceNullArguments() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("k", "v");
        try {
            map.replace(null, "v", "n");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {}
        try {
            map.replace("k", null, "n");
            throw new AssertionError("Expected NullPointerException for null oldValue");
        } catch (NullPointerException expected) {}
        try {
            map.replace("k", "v", null);
            throw new AssertionError("Expected NullPointerException for null newValue");
        } catch (NullPointerException expected) {}
    }

    static void testReplaceNodeNotFound() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean result = map.replace("absent", "any", "value");
        if (result) {
            throw new AssertionError("Expected replace to return false for absent key");
        }
    }
}

/*
 * @test
 * @summary Test replace method of ConcurrentSkipListMap for correct behavior
 * @run main ConcurrentSkipListMapWitupTest_P834f964729ee
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P834f964729ee {

    public static void main(String[] args) {
        testReplaceSuccess();
        testReplaceFailDifferentOldValue();
        testReplaceFailKeyNotPresent();
        testReplaceNullArguments();
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
            throw new AssertionError("Expected value to be 'value2', but was: " + result);
        }
    }

    static void testReplaceFailDifferentOldValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key2";
        map.put(key, "initial");
        boolean replaced = map.replace(key, "wrongOldValue", "newValue");
        if (replaced) {
            throw new AssertionError("Expected replace to fail due to wrong old value");
        }
        String currentValue = map.get(key);
        if (!"initial".equals(currentValue)) {
            throw new AssertionError("Value should remain 'initial', but was: " + currentValue);
        }
    }

    static void testReplaceFailKeyNotPresent() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean replaced = map.replace("absentKey", "anyOld", "anyNew");
        if (replaced) {
            throw new AssertionError("Expected replace to fail for absent key");
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
}

/*
 * @test
 * @summary Test replace method of ConcurrentSkipListMap for correct behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testReplaceExistingKey();
        testReplaceNonExistingKey();
        testReplaceNullKey();
        testReplaceNullValue();
    }

    static void testReplaceExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key1";
        String initialValue = "value1";
        String newValue = "value2";
        map.put(key, initialValue);
        String oldValue = map.replace(key, newValue);
        if (oldValue == null || !oldValue.equals(initialValue)) {
            throw new AssertionError("Expected old value to be 'value1', but got: " + oldValue);
        }
        String currentValue = map.get(key);
        if (!currentValue.equals(newValue)) {
            throw new AssertionError("Expected current value to be 'value2', but got: " + currentValue);
        }
    }

    static void testReplaceNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "nonexistent";
        String value = "value";
        String result = map.replace(key, value);
        if (result != null) {
            throw new AssertionError("Expected null when replacing non-existing key, but got: " + result);
        }
    }

    static void testReplaceNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replace(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReplaceNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "value");
        try {
            map.replace("key", null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

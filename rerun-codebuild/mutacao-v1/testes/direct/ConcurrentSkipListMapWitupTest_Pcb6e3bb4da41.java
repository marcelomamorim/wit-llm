/*
 * @test
 * @summary Test putIfAbsent method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_Pcb6e3bb4da41
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Pcb6e3bb4da41 {
    public static void main(String[] args) {
        testPutIfAbsentNewKey();
        testPutIfAbsentExistingKey();
        testPutIfAbsentNullValue();
    }

    static void testPutIfAbsentNewKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "newKey";
        String value = "value";
        String result = map.putIfAbsent(key, value);
        if (result != null) {
            throw new AssertionError("Expected null for new key, got: " + result);
        }
        if (!value.equals(map.get(key))) {
            throw new AssertionError("Value not inserted correctly for new key");
        }
    }

    static void testPutIfAbsentExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "existingKey";
        String initialValue = "initial";
        map.put(key, initialValue);
        String newValue = "newValue";
        String result = map.putIfAbsent(key, newValue);
        if (result == null || !result.equals(initialValue)) {
            throw new AssertionError("Expected previous value for existing key, got: " + result);
        }
        if (!initialValue.equals(map.get(key))) {
            throw new AssertionError("Value for existing key was changed");
        }
    }

    static void testPutIfAbsentNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.putIfAbsent("key", null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException e) {
            // expected
        }
    }
}

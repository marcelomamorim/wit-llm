/*
 * @test
 * @summary Test putIfAbsent method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testPutIfAbsentNewKey();
        testPutIfAbsentExistingKey();
    }

    private static void testPutIfAbsentNewKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "newKey";
        String value = "value1";
        String result = map.putIfAbsent(key, value);
        if (result != null) {
            throw new AssertionError("Expected null for new key, but got: " + result);
        }
        if (!value.equals(map.get(key))) {
            throw new AssertionError("Value not correctly inserted for new key") ;
        }
    }

    private static void testPutIfAbsentExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "existingKey";
        String initialValue = "initial";
        map.put(key, initialValue);
        String newValue = "newValue";
        String result = map.putIfAbsent(key, newValue);
        if (result == null || !result.equals(initialValue)) {
            throw new AssertionError("Expected previous value to be 'initial', but got: " + result);
        }
        if (!initialValue.equals(map.get(key))) {
            throw new AssertionError("Value should not have been replaced") ;
        }
    }
}

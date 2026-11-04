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
        testPutIfAbsentNullValue();
    }

    static void testPutIfAbsentNewKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key1";
        String value = "value1";
        String result = map.putIfAbsent(key, value);
        assert result == null : "Expected null when inserting new key";
        assert "value1".equals(map.get(key)) : "Value not inserted correctly";
    }

    static void testPutIfAbsentExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key2";
        String initialValue = "initial";
        map.put(key, initialValue);
        String newValue = "new";
        String result = map.putIfAbsent(key, newValue);
        assert result.equals(initialValue) : "Expected existing value to be returned";
        assert map.get(key).equals(initialValue) : "Value should not be overwritten";
    }

    static void testPutIfAbsentNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.putIfAbsent("key3", null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}

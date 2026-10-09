/*
 * @test    @summary Test putIfAbsent method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_P281c30380e98
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P281c30380e98 {
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
        assert result == null : "Expected null for new key";
        assert "value".equals(map.get(key)) : "Value not inserted correctly";
    }

    static void testPutIfAbsentExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "existingKey";
        String initialValue = "initial";
        map.put(key, initialValue);
        String newValue = "newValue";
        String result = map.putIfAbsent(key, newValue);
        assert result.equals(initialValue) : "Expected existing value to be returned";
        assert map.get(key).equals(initialValue) : "Value should not be overwritten";
    }

    static void testPutIfAbsentNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.putIfAbsent("nullKey", null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}

/*
 * @test
 * @summary Test put method of ConcurrentSkipListMap for expected behavior
 * @run main ConcurrentSkipListMapWitupTest_Pa199c7aff357
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Pa199c7aff357 {

    public static void main(String[] args) {
        testPutWithValidKeyAndValue();
        testPutWithNullValueThrowsException();
        testPutWithNullKey();
    }

    static void testPutWithValidKeyAndValue() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        String key = "testKey";
        Integer value = 42;
        Integer previous = map.put(key, value);
        assert previous == null : "Expected null as previous value for new key";
        Integer retrieved = map.get(key);
        assert retrieved.equals(value) : "Value retrieved does not match value inserted";
    }

    static void testPutWithNullValueThrowsException() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        try {
            map.put("key", null);
            assert false : "Expected NullPointerException when putting null value";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testPutWithNullKey() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        try {
            map.put(null, 1);
            assert false : "Expected NullPointerException when putting null key";
        } catch (NullPointerException e) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Test putIfAbsent method of ConcurrentHashMap
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testPutIfAbsentWithNewKey();
        testPutIfAbsentWithExistingKey();
        testPutIfAbsentWithNullKey();
        testPutIfAbsentWithNullValue();
    }

    static void testPutIfAbsentWithNewKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.putIfAbsent("key1", "value1");
        if (!"value1".equals(result) && map.get("key1") == null) {
            throw new AssertionError("Failed: putIfAbsent with new key did not insert value correctly.");
        }
        if (!"value1".equals(map.get("key1"))) {
            throw new AssertionError("Failed: putIfAbsent with new key did not store value correctly.");
        }
    }

    static void testPutIfAbsentWithExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key2", "initial");
        String result = map.putIfAbsent("key2", "newValue");
        if (!"initial".equals(result)) {
            throw new AssertionError("Failed: putIfAbsent with existing key returned wrong previous value.");
        }
        if (!"initial".equals(map.get("key2"))) {
            throw new AssertionError("Failed: putIfAbsent with existing key changed the value.");
        }
    }

    static void testPutIfAbsentWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.putIfAbsent(null, "nullValue");
            if (!"nullValue".equals(map.get(null))) {
                throw new AssertionError("Failed: putIfAbsent with null key did not store value.");
            }
        } catch (NullPointerException e) {
            // Expected behavior if null keys are not allowed
        }
    }

    static void testPutIfAbsentWithNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.putIfAbsent("nullValueKey", null);
        if (result != null) {
            throw new AssertionError("Failed: putIfAbsent with null value returned wrong previous value.");
        }
        if (map.get("nullValueKey") != null) {
            throw new AssertionError("Failed: putIfAbsent with null value did not store null.");
        }
    }
}

/*
 * @test
 * @summary Test ConcurrentHashMap.putIfAbsent behavior including null key/value rejection
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testPutIfAbsentNormal();
        testPutIfAbsentNullKey();
        testPutIfAbsentNullValue();
    }

    private static void testPutIfAbsentNormal() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String prev = map.putIfAbsent("key1", "value1");
        if (prev != null) {
            throw new AssertionError("Expected null on first putIfAbsent");
        }
        String val = map.putIfAbsent("key1", "value2");
        if (!"value1".equals(val)) {
            throw new AssertionError("Expected existing value to be returned on putIfAbsent");
        }
        if (!"value1".equals(map.get("key1"))) {
            throw new AssertionError("Value should not be replaced by putIfAbsent");
        }
    }

    private static void testPutIfAbsentNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.putIfAbsent(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }
    }

    private static void testPutIfAbsentNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.putIfAbsent("key", null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
        }
    }
}

/*
 * @test
 * @summary Basic tests for ConcurrentHashMap.put(K,V) method
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testPutNewKey();
        testPutOverwriteValue();
        testPutNullKey();
        testPutNullValue();
    }

    private static void testPutNewKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String prev = map.put("key1", "value1");
        if (prev != null) {
            throw new RuntimeException("Expected null on first put, got: " + prev);
        }
        String val = map.get("key1");
        if (!"value1".equals(val)) {
            throw new RuntimeException("Expected value1 after put, got: " + val);
        }
    }

    private static void testPutOverwriteValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key2", "value2");
        String prev = map.put("key2", "value3");
        if (!"value2".equals(prev)) {
            throw new RuntimeException("Expected previous value2 on overwrite, got: " + prev);
        }
        String val = map.get("key2");
        if (!"value3".equals(val)) {
            throw new RuntimeException("Expected value3 after overwrite, got: " + val);
        }
    }

    private static void testPutNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.put(null, "value");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testPutNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.put("key", null);
            throw new RuntimeException("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

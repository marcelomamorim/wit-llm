/* @test    @summary Testa o método put de ConcurrentHashMap com entradas válidas e nulas    @run main ConcurrentHashMapWitupTest */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testPutNormal();
        testPutNullKey();
        testPutNullValue();
    }

    static void testPutNormal() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String prev = map.put("key1", "value1");
        if (prev != null) {
            throw new AssertionError("Expected null for new key, got: " + prev);
        }
        String result = map.get("key1");
        if (!"value1".equals(result)) {
            throw new AssertionError("Expected 'value1', got: " + result);
        }
    }

    static void testPutNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.put(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testPutNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String prev = map.put("key2", null);
        if (prev != null) {
            throw new AssertionError("Expected null for previous value, got: " + prev);
        }
        String result = map.get("key2");
        if (result != null) {
            throw new AssertionError("Expected null value, got: " + result);
        }
    }
}

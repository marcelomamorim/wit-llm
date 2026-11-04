/*
 * @test
 * @summary Test ConcurrentHashMap.replace(K key, V value) method behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testReplaceReturnsNullWhenKeyAbsent();
        testReplaceReturnsOldValueWhenKeyPresent();
        testReplaceThrowsNPEForNullKey();
        testReplaceThrowsNPEForNullValue();
    }

    private static void testReplaceReturnsNullWhenKeyAbsent() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.replace("absentKey", "newValue");
        if (result != null) {
            throw new AssertionError("Expected null when replacing absent key, got: " + result);
        }
    }

    private static void testReplaceReturnsOldValueWhenKeyPresent() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "oldValue");
        String result = map.replace("key", "newValue");
        if (!"oldValue".equals(result)) {
            throw new AssertionError("Expected old value 'oldValue', got: " + result);
        }
        String current = map.get("key");
        if (!"newValue".equals(current)) {
            throw new AssertionError("Expected new value 'newValue' after replace, got: " + current);
        }
    }

    private static void testReplaceThrowsNPEForNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceThrowsNPEForNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace("key", null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

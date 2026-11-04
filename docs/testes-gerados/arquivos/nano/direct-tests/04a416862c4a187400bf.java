/*
 * @test    @summary Test the contains method of ConcurrentHashMap
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testContainsWithExistingValue();
        testContainsWithNonExistingValue();
        testContainsWithNullValue();
    }

    static void testContainsWithExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String key = "key1";
        String value = "value1";
        map.put(key, value);
        boolean result = map.contains(value);
        if (!result) {
            throw new AssertionError("Expected map to contain value: " + value);
        }
    }

    static void testContainsWithNonExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = map.contains("nonexistent_value");
        if (result) {
            throw new AssertionError("Did not expect map to contain value: nonexistent_value");
        }
    }

    static void testContainsWithNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", null);
        boolean result = map.contains(null);
        if (!result) {
            throw new AssertionError("Expected map to contain null value");
        }
    }
}

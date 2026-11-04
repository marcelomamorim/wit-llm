/*
 * @test    @summary Test for ConcurrentHashMap.contains(Object value)
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithExistingValue();
        testContainsWithNonExistingValue();
    }

    private static void testContainsWithNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = false;
        try {
            result = map.contains(null);
        } catch (NullPointerException e) {
            // Expected exception
        }
        if (result) {
            throw new AssertionError("Expected NullPointerException for null argument");
        }
    }

    private static void testContainsWithExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = map.contains("value1");
        if (!result) {
            throw new AssertionError("Map should contain value 'value1'");
        }
    }

    private static void testContainsWithNonExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean result = map.contains("nonexistent");
        if (result) {
            throw new AssertionError("Map should not contain value 'nonexistent'");
        }
    }
}

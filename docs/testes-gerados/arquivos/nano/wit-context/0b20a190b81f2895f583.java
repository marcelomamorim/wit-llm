/*
 * @test
 * @summary Test for ConcurrentSkipListMap.get method, including null key handling
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testGetWithNullKey();
        testGetWithExistingKey();
        testGetWithNonExistingKey();
    }

    static void testGetWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.get(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null key");
        }
    }

    static void testGetWithExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "existing";
        String value = "value";
        map.put(key, value);
        String result = map.get(key);
        if (result == null || !result.equals(value)) {
            throw new AssertionError("Expected value for existing key");
        }
    }

    static void testGetWithNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "nonexistent";
        String result = map.get(key);
        if (result != null) {
            throw new AssertionError("Expected null for non-existing key");
        }
    }
}

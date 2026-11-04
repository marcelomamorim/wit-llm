/*
 * @test
 * @summary Test getOrDefault method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testGetOrDefaultWithExistingKey();
        testGetOrDefaultWithNonExistingKey();
        testGetOrDefaultWithNullKey();
        testGetOrDefaultWithNullDefault();
    }

    static void testGetOrDefaultWithExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        String result = map.getOrDefault("key1", "default");
        if (!"value1".equals(result)) {
            throw new AssertionError("Expected 'value1', but got: " + result);
        }
    }

    static void testGetOrDefaultWithNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String result = map.getOrDefault("nonexistent", "default");
        if (!"default".equals(result)) {
            throw new AssertionError("Expected 'default', but got: " + result);
        }
    }

    static void testGetOrDefaultWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put(null, "nullValue");
        String result = map.getOrDefault(null, "default");
        if (!"nullValue".equals(result)) {
            throw new AssertionError("Expected 'nullValue', but got: " + result);
        }
    }

    static void testGetOrDefaultWithNullDefault() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key2", "value2");
        String result = map.getOrDefault("nonexistent", null);
        if (result != null) {
            throw new AssertionError("Expected null, but got: " + result);
        }
    }
}

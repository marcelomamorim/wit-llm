/*
 * @test
 * @summary Test ConcurrentSkipListMap.getOrDefault behavior including null key exception
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test getOrDefault returns defaultValue when key not present
        String defaultVal = "default";
        String result = map.getOrDefault("missingKey", defaultVal);
        if (!defaultVal.equals(result)) {
            throw new AssertionError("Expected default value when key is missing");
        }

        // Test getOrDefault returns value when key present
        map.put("key1", "value1");
        String val = map.getOrDefault("key1", defaultVal);
        if (!"value1".equals(val)) {
            throw new AssertionError("Expected stored value to be returned");
        }

        // Test getOrDefault throws NullPointerException when key is null
        try {
            map.getOrDefault(null, defaultVal);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

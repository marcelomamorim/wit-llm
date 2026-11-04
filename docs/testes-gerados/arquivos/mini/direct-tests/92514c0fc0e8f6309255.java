/*
 * @test
 * @summary Test ConcurrentSkipListMap.getOrDefault behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test getOrDefault returns defaultValue when key is absent
        String defaultVal = "default";
        String result = map.getOrDefault("missingKey", defaultVal);
        if (!defaultVal.equals(result)) {
            throw new AssertionError("Expected default value when key is absent");
        }

        // Test getOrDefault returns value when key is present
        map.put("key1", "value1");
        String val = map.getOrDefault("key1", defaultVal);
        if (!"value1".equals(val)) {
            throw new AssertionError("Expected stored value when key is present");
        }

        // Test getOrDefault with null defaultValue and absent key returns null
        String nullDefault = null;
        String nullResult = map.getOrDefault("absent", nullDefault);
        if (nullResult != null) {
            throw new AssertionError("Expected null when defaultValue is null and key is absent");
        }

        // Test getOrDefault with null key returns defaultValue (ConcurrentSkipListMap does not allow null keys, expect NullPointerException)
        boolean npeThrown = false;
        try {
            map.getOrDefault(null, defaultVal);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when key is null");
        }
    }
}

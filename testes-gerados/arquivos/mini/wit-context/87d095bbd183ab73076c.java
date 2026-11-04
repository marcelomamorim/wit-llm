/*
 * @test
 * @summary Test ConcurrentSkipListMap.containsValue behavior including null input
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        // Test containsValue with existing value
        if (!map.containsValue("value1")) {
            throw new AssertionError("containsValue should return true for existing value");
        }

        // Test containsValue with non-existing value
        if (map.containsValue("value3")) {
            throw new AssertionError("containsValue should return false for non-existing value");
        }

        // Test containsValue with null value - expect NullPointerException
        try {
            map.containsValue(null);
            throw new AssertionError("containsValue(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test containsValue with value present but mapped to null (simulate by putting null value)
        map.put("key3", null);
        if (map.containsValue(null)) {
            throw new AssertionError("containsValue should return false for null values in map");
        }
    }
}

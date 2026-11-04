/*
 * @test
 * @summary Test ConcurrentSkipListMap.containsKey behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test containsKey on empty map returns false
        if (map.containsKey("key1")) {
            throw new AssertionError("containsKey should return false for empty map");
        }

        // Put a key-value pair and test containsKey returns true
        map.put("key1", "value1");
        if (!map.containsKey("key1")) {
            throw new AssertionError("containsKey should return true for existing key");
        }

        // Test containsKey returns false for a key not present
        if (map.containsKey("key2")) {
            throw new AssertionError("containsKey should return false for non-existing key");
        }

        // Test containsKey with null key throws NullPointerException
        boolean npeThrown = false;
        try {
            map.containsKey(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("containsKey should throw NullPointerException for null key");
        }
    }
}

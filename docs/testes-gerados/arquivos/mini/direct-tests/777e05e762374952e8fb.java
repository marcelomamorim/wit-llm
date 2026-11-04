/*
 * @test
 * @summary Test ConcurrentSkipListMap.containsKey behavior including null key rejection and key presence
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test containsKey on empty map returns false
        if (map.containsKey("absent")) {
            throw new AssertionError("containsKey returned true for absent key on empty map");
        }

        // Put a key and test containsKey returns true
        map.put("key1", "value1");
        if (!map.containsKey("key1")) {
            throw new AssertionError("containsKey returned false for present key");
        }

        // Test containsKey returns false for a different key
        if (map.containsKey("key2")) {
            throw new AssertionError("containsKey returned true for a key not in map");
        }

        // Test containsKey throws NullPointerException for null key
        try {
            map.containsKey(null);
            throw new AssertionError("containsKey did not throw NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

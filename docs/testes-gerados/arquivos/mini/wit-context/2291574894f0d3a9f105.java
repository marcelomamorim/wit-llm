/*
 * @test
 * @summary Test ConcurrentSkipListMap.containsKey behavior including null key handling
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test containsKey on empty map returns false
        if (map.containsKey("missing")) {
            throw new AssertionError("containsKey returned true for missing key");
        }

        // Test containsKey after put returns true
        map.put("key1", "value1");
        if (!map.containsKey("key1")) {
            throw new AssertionError("containsKey returned false for existing key");
        }

        // Test containsKey with a key not present returns false
        if (map.containsKey("key2")) {
            throw new AssertionError("containsKey returned true for non-existing key");
        }

        // Test containsKey with null key throws NullPointerException
        try {
            map.containsKey(null);
            throw new AssertionError("containsKey did not throw NullPointerException for null key");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}

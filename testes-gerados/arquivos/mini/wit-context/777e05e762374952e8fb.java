/*
 * @test
 * @summary Test ConcurrentSkipListMap.containsKey behavior including null key check
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        // Test containsKey with existing key
        if (!map.containsKey("key1")) {
            throw new AssertionError("containsKey should return true for existing key");
        }

        // Test containsKey with non-existing key
        if (map.containsKey("key3")) {
            throw new AssertionError("containsKey should return false for non-existing key");
        }

        // Test containsKey with null key - expect NullPointerException
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

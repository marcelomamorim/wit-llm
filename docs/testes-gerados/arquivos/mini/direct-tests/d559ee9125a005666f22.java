/*
 * @test
 * @summary Basic tests for ConcurrentSkipListMap.remove(Object key)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test remove on empty map returns null
        if (map.remove("key") != null) {
            throw new AssertionError("Expected null when removing from empty map");
        }

        // Test remove on existing key returns the value and removes the key
        map.put("key1", "value1");
        String removed = map.remove("key1");
        if (!"value1".equals(removed)) {
            throw new AssertionError("Expected value1 when removing existing key");
        }
        if (map.containsKey("key1")) {
            throw new AssertionError("Key should be removed after remove call");
        }

        // Test remove on non-existing key returns null
        if (map.remove("nonexistent") != null) {
            throw new AssertionError("Expected null when removing non-existing key");
        }

        // Test remove with null key throws NullPointerException
        boolean npeThrown = false;
        try {
            map.remove(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when removing null key");
        }
    }
}

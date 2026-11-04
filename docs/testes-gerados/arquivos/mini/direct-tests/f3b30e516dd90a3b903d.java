/*
 * @test
 * @summary Basic tests for ConcurrentSkipListMap.get(Object) method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test get on empty map returns null
        if (map.get("key") != null) {
            throw new AssertionError("Expected null for get on empty map");
        }

        // Put a key-value pair and test get returns the value
        map.put("key1", "value1");
        String v = map.get("key1");
        if (!"value1".equals(v)) {
            throw new AssertionError("Expected value1 for key1, got: " + v);
        }

        // Test get with a key not present returns null
        if (map.get("key2") != null) {
            throw new AssertionError("Expected null for non-existent key");
        }

        // Test get with null key throws NullPointerException
        boolean npeThrown = false;
        try {
            map.get(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null key");
        }

        // Test get with a key of different type (Integer) returns null or throws ClassCastException
        // According to ConcurrentSkipListMap spec, keys must be mutually comparable
        // So this may throw ClassCastException
        boolean cceThrown = false;
        try {
            map.get(Integer.valueOf(1));
        } catch (ClassCastException e) {
            cceThrown = true;
        }
        if (!cceThrown) {
            // If no exception, get should return null
            Object val = map.get(Integer.valueOf(1));
            if (val != null) {
                throw new AssertionError("Expected null or ClassCastException for incompatible key type");
            }
        }
    }
}

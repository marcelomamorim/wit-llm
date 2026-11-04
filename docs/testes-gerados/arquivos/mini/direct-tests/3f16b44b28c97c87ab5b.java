/*
 * @test
 * @summary Basic tests for TreeMap.get(Object) method
 * @run main java.util.TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<String, String> map = new TreeMap<>();

        // Test get on empty map returns null
        if (map.get("key") != null) {
            throw new Error("Expected null for get on empty map");
        }

        // Put a key-value pair and test get returns the value
        map.put("key", "value");
        String val = map.get("key");
        if (!"value".equals(val)) {
            throw new Error("Expected 'value' for key 'key', got: " + val);
        }

        // Test get with a key not present returns null
        if (map.get("nokey") != null) {
            throw new Error("Expected null for get with non-existent key");
        }

        // Test get with null key on map that does not allow null keys
        // TreeMap does not allow null keys by default, so get(null) throws NullPointerException
        boolean npeThrown = false;
        try {
            map.get(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new Error("Expected NullPointerException for get(null) on natural ordering TreeMap");
        }

        // Test get with null key on TreeMap with Comparator that allows null keys
        TreeMap<String, String> nullFriendlyMap = new TreeMap<>(
            (a, b) -> {
                if (a == null && b == null) return 0;
                if (a == null) return -1;
                if (b == null) return 1;
                return a.compareTo(b);
            }
        );
        nullFriendlyMap.put(null, "nullValue");
        String nullVal = nullFriendlyMap.get(null);
        if (!"nullValue".equals(nullVal)) {
            throw new Error("Expected 'nullValue' for get(null) on null-friendly TreeMap, got: " + nullVal);
        }

        System.out.println("All TreeMap.get tests passed.");
    }
}

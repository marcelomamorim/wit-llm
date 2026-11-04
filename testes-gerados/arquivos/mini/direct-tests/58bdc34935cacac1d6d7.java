/*
 * @test
 * @summary Test TreeMap.containsKey(Object) method behavior
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<>();

        // Test containsKey on empty map
        if (map.containsKey("key1")) {
            throw new RuntimeException("Fail: containsKey should return false on empty map");
        }

        // Put a key and test containsKey returns true
        map.put("key1", 1);
        if (!map.containsKey("key1")) {
            throw new RuntimeException("Fail: containsKey should return true for existing key");
        }

        // Test containsKey for a key not present
        if (map.containsKey("key2")) {
            throw new RuntimeException("Fail: containsKey should return false for non-existing key");
        }

        // Test containsKey with null key if allowed (TreeMap does not allow null keys by default)
        boolean nullKeyAllowed = false;
        try {
            map.containsKey(null);
            nullKeyAllowed = true;
        } catch (NullPointerException npe) {
            // expected for natural ordering TreeMap
        }

        if (nullKeyAllowed) {
            // If null keys allowed, test containsKey returns false for null key when not present
            if (map.containsKey(null)) {
                throw new RuntimeException("Fail: containsKey should return false for null key not present");
            }
        }

        System.out.println("TreeMap.containsKey tests passed");
    }
}

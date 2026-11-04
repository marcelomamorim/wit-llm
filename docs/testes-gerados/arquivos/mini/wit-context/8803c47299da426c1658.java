/*
 * @test
 * @summary Test ConcurrentHashMap remove(Object) method behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        // Test remove with null
        // According to source, remove(Object o) returns false if o is not a Map.Entry or key/value null
        if (map.remove(null)) {
            throw new AssertionError("remove(null) should return false");
        }

        // Test remove with non-Map.Entry object
        if (map.remove("not an entry")) {
            throw new AssertionError("remove(non-Map.Entry) should return false");
        }

        // Test remove with Map.Entry with null key
        Map.Entry<String, String> entryWithNullKey = new Map.Entry<String, String>() {
            public String getKey() { return null; }
            public String getValue() { return "value"; }
            public String setValue(String value) { throw new UnsupportedOperationException(); }
        };
        if (map.remove(entryWithNullKey)) {
            throw new AssertionError("remove(entry with null key) should return false");
        }

        // Test remove with Map.Entry with null value
        Map.Entry<String, String> entryWithNullValue = new Map.Entry<String, String>() {
            public String getKey() { return "key"; }
            public String getValue() { return null; }
            public String setValue(String value) { throw new UnsupportedOperationException(); }
        };
        if (map.remove(entryWithNullValue)) {
            throw new AssertionError("remove(entry with null value) should return false");
        }

        // Test remove with Map.Entry with key and value not present in map
        Map.Entry<String, String> entryNotPresent = new Map.Entry<String, String>() {
            public String getKey() { return "key"; }
            public String getValue() { return "value"; }
            public String setValue(String value) { throw new UnsupportedOperationException(); }
        };
        if (map.remove(entryNotPresent)) {
            throw new AssertionError("remove(entry not present) should return false");
        }

        // Put a key-value pair and remove it via entry
        map.put("key", "value");
        if (!map.remove(entryNotPresent)) {
            // entryNotPresent has key "key" and value "value" matching map
            // so remove should succeed
            throw new AssertionError("remove(entry present) should return true");
        }

        // Confirm map is empty after removal
        if (!map.isEmpty()) {
            throw new AssertionError("map should be empty after removal");
        }

        System.out.println("All tests passed.");
    }
}

/*
 * @test
 * @summary Test ConcurrentSkipListMap.replace(K key, V oldValue, V newValue) method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test NullPointerException for null key
        try {
            map.replace(null, "old", "new");
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }

        // Test NullPointerException for null oldValue
        try {
            map.replace("key", null, "new");
            throw new Error("Expected NullPointerException for null oldValue");
        } catch (NullPointerException expected) {
        }

        // Test NullPointerException for null newValue
        try {
            map.replace("key", "old", null);
            throw new Error("Expected NullPointerException for null newValue");
        } catch (NullPointerException expected) {
        }

        // Test replace returns false if key not present
        boolean replaced = map.replace("key", "old", "new");
        if (replaced) {
            throw new Error("Expected replace to return false when key not present");
        }

        // Put initial value
        map.put("key", "old");

        // Test replace returns false if oldValue does not match
        replaced = map.replace("key", "wrongOld", "new");
        if (replaced) {
            throw new Error("Expected replace to return false when oldValue does not match");
        }

        // Test replace returns true and updates value if oldValue matches
        replaced = map.replace("key", "old", "new");
        if (!replaced) {
            throw new Error("Expected replace to return true when oldValue matches");
        }
        String val = map.get("key");
        if (!"new".equals(val)) {
            throw new Error("Expected value to be updated to 'new', but was: " + val);
        }

        // Test replace returns false if value was concurrently removed (simulate by removing)
        map.remove("key");
        replaced = map.replace("key", "new", "newer");
        if (replaced) {
            throw new Error("Expected replace to return false when key removed concurrently");
        }

        System.out.println("All tests passed.");
    }
}

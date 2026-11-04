/*
 * @test
 * @summary Test ConcurrentSkipListMap.replace(K key, V oldValue, V newValue) method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test replace on empty map returns false
        boolean replaced = map.replace("key", "old", "new");
        if (replaced) {
            throw new RuntimeException("replace returned true on non-existing key");
        }

        // Put a key-value pair
        map.put("key", "old");

        // Replace with correct oldValue returns true
        replaced = map.replace("key", "old", "new");
        if (!replaced) {
            throw new RuntimeException("replace returned false when oldValue matched");
        }

        // Verify value was replaced
        String val = map.get("key");
        if (!"new".equals(val)) {
            throw new RuntimeException("Value was not replaced correctly");
        }

        // Replace with incorrect oldValue returns false
        replaced = map.replace("key", "old", "newer");
        if (replaced) {
            throw new RuntimeException("replace returned true when oldValue did not match");
        }

        // Verify value remains unchanged
        val = map.get("key");
        if (!"new".equals(val)) {
            throw new RuntimeException("Value changed incorrectly when oldValue did not match");
        }

        // Test replace with null key throws NullPointerException
        try {
            map.replace(null, "any", "any");
            throw new RuntimeException("replace did not throw NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test replace with null oldValue and newValue on existing key
        map.put("nullValueKey", null);
        replaced = map.replace("nullValueKey", null, "notNull");
        if (!replaced) {
            throw new RuntimeException("replace returned false when oldValue was null and matched");
        }
        val = map.get("nullValueKey");
        if (!"notNull".equals(val)) {
            throw new RuntimeException("Value was not replaced correctly when oldValue was null");
        }

        // Test replace with null oldValue that does not match
        replaced = map.replace("nullValueKey", null, "newer");
        if (replaced) {
            throw new RuntimeException("replace returned true when oldValue was null but did not match");
        }

        System.out.println("All tests passed.");
    }
}

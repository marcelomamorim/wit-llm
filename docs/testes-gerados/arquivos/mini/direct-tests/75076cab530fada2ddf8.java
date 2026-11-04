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
            throw new RuntimeException("replace should return false when key is absent");
        }

        // Put initial value
        map.put("key", "old");

        // Replace with matching oldValue returns true and updates value
        replaced = map.replace("key", "old", "new");
        if (!replaced) {
            throw new RuntimeException("replace should return true when oldValue matches");
        }
        String val = map.get("key");
        if (!"new".equals(val)) {
            throw new RuntimeException("value should be updated to 'new'");
        }

        // Replace with non-matching oldValue returns false and does not update
        replaced = map.replace("key", "old", "newer");
        if (replaced) {
            throw new RuntimeException("replace should return false when oldValue does not match");
        }
        val = map.get("key");
        if (!"new".equals(val)) {
            throw new RuntimeException("value should remain 'new' when oldValue does not match");
        }

        // Replace with null oldValue and null newValue
        map.put("nullKey", null);
        replaced = map.replace("nullKey", null, "notNull");
        if (!replaced) {
            throw new RuntimeException("replace should return true when oldValue is null and matches");
        }
        val = map.get("nullKey");
        if (!"notNull".equals(val)) {
            throw new RuntimeException("value should be updated to 'notNull'");
        }

        // Replace with null newValue (removal)
        replaced = map.replace("nullKey", "notNull", null);
        if (!replaced) {
            throw new RuntimeException("replace should return true when oldValue matches and newValue is null");
        }
        val = map.get("nullKey");
        if (val != null) {
            throw new RuntimeException("value should be removed (null) after replace with null newValue");
        }

        // Test that replace throws NullPointerException if key is null
        try {
            map.replace(null, "old", "new");
            throw new RuntimeException("replace should throw NullPointerException when key is null");
        } catch (NullPointerException expected) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}

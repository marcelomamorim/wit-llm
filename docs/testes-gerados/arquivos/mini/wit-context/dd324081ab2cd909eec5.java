/*
 * @test
 * @summary Test ConcurrentSkipListMap.replace(K,V) for null checks and replace behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test that replace throws NullPointerException if key is null
        try {
            map.replace(null, "value");
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }

        // Test that replace throws NullPointerException if value is null
        try {
            map.replace("key", null);
            throw new Error("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
        }

        // Test replace returns null if key not present
        String result = map.replace("absentKey", "newValue");
        if (result != null) {
            throw new Error("Expected null return when replacing absent key");
        }

        // Put initial mapping
        map.put("key1", "value1");

        // Replace existing key with new value
        String oldValue = map.replace("key1", "value2");
        if (!"value1".equals(oldValue)) {
            throw new Error("Expected old value 'value1' on replace, got: " + oldValue);
        }

        // Confirm new value is set
        String currentValue = map.get("key1");
        if (!"value2".equals(currentValue)) {
            throw new Error("Expected new value 'value2' after replace, got: " + currentValue);
        }

        // Replace again with same value
        oldValue = map.replace("key1", "value2");
        if (!"value2".equals(oldValue)) {
            throw new Error("Expected old value 'value2' on replace with same value, got: " + oldValue);
        }

        System.out.println("All tests passed.");
    }
}

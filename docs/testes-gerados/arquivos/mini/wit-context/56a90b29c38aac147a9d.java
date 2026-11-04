/*
 * @test
 * @summary Test ConcurrentSkipListMap.put(K,V) for null key and null value exceptions
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test that put throws NullPointerException if value is null
        try {
            map.put("key", null);
            throw new Error("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test that put throws NullPointerException if key is null and value is non-null
        try {
            map.put(null, "value");
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test normal put with non-null key and value
        String prev = map.put("key", "value");
        if (prev != null) {
            throw new Error("Expected previous value to be null on first put");
        }

        // Test that put replaces existing value and returns old value
        String old = map.put("key", "newValue");
        if (!"value".equals(old)) {
            throw new Error("Expected old value 'value' but got " + old);
        }

        // Verify map contains the new value
        String current = map.get("key");
        if (!"newValue".equals(current)) {
            throw new Error("Expected current value 'newValue' but got " + current);
        }
    }
}

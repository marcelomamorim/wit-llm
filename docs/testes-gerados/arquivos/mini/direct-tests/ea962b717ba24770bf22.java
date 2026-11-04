/*
 * @test
 * @summary Test ConcurrentSkipListMap.replace(K key, V value) method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test replace on absent key returns null
        String replaced = map.replace("key1", "value1");
        if (replaced != null) {
            throw new AssertionError("Expected null when replacing absent key");
        }

        // Put a key and replace it
        map.put("key1", "valueOld");
        replaced = map.replace("key1", "valueNew");
        if (!"valueOld".equals(replaced)) {
            throw new AssertionError("Expected old value to be returned on replace");
        }

        // Verify the new value is set
        String current = map.get("key1");
        if (!"valueNew".equals(current)) {
            throw new AssertionError("Expected new value to be set after replace");
        }

        // Test replace with null key throws NullPointerException
        try {
            map.replace(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }

        // Test replace with null value allowed (ConcurrentSkipListMap allows null values? No, it does not allow null values)
        // So test that null value throws NullPointerException
        try {
            map.replace("key1", null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
        }
    }
}

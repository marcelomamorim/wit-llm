/*
 * @test
 * @summary Test ConcurrentSkipListMap.replace(K key, V value) for null key and normal replacement
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test replace with null key - expect NullPointerException
        try {
            map.replace(null, "value");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test replace with absent key - expect null return
        String absentKey = "absent";
        String result = map.replace(absentKey, "newValue");
        if (result != null) {
            throw new RuntimeException("Expected null return when replacing absent key");
        }

        // Test replace with present key - expect old value returned
        String key = "key";
        String oldValue = "oldValue";
        String newValue = "newValue";
        map.put(key, oldValue);
        String replaced = map.replace(key, newValue);
        if (!oldValue.equals(replaced)) {
            throw new RuntimeException("Expected old value to be returned on replace");
        }

        // Verify that the value was updated
        String currentValue = map.get(key);
        if (!newValue.equals(currentValue)) {
            throw new RuntimeException("Expected value to be updated after replace");
        }
    }
}

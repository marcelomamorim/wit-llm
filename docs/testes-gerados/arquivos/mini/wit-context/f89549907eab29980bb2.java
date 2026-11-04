/*
 * @test
 * @summary Test ConcurrentSkipListMap.put(K,V) for null key and normal insertion
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test 1: put with null key should throw NullPointerException
        try {
            map.put(null, "value");
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: put with non-null key should insert and return null (no previous mapping)
        String prev = map.put("key1", "value1");
        if (prev != null) {
            throw new Error("Expected null return for new key insertion");
        }

        // Test 3: put with existing key should return previous value
        prev = map.put("key1", "value2");
        if (!"value1".equals(prev)) {
            throw new Error("Expected previous value 'value1' when replacing key1");
        }

        // Test 4: verify updated value
        String val = map.get("key1");
        if (!"value2".equals(val)) {
            throw new Error("Expected updated value 'value2' for key1");
        }
    }
}

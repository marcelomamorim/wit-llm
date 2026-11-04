/*
 * @test
 * @summary Test ConcurrentSkipListMap.get(Object) for null key and normal key
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");

        // Test that get(null) throws NullPointerException
        try {
            map.get(null);
            throw new RuntimeException("Expected NullPointerException for get(null)");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test that get returns null for absent key
        if (map.get("absent") != null) {
            throw new RuntimeException("Expected null for absent key");
        }

        // Test that get returns correct value for present key
        String v = map.get("key1");
        if (!"value1".equals(v)) {
            throw new RuntimeException("Expected value1 for key1, got: " + v);
        }
    }
}

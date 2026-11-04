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

        // Test normal get returns expected value
        String v = map.get("key1");
        if (!"value1".equals(v)) {
            throw new AssertionError("Expected value1 but got " + v);
        }

        // Test get with null key throws NullPointerException
        try {
            map.get(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test get with absent key returns null
        String absent = map.get("absent");
        if (absent != null) {
            throw new AssertionError("Expected null for absent key but got " + absent);
        }
    }
}

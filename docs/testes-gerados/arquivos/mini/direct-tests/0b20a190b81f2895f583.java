/*
 * @test
 * @summary Test ConcurrentSkipListMap.get(Object) method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test get with null key throws NullPointerException
        try {
            map.get(null);
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }

        // Test get on empty map returns null
        String result = map.get("absentKey");
        if (result != null) {
            throw new Error("Expected null for absent key in empty map");
        }

        // Test get returns correct value for present key
        map.put("key1", "value1");
        String val = map.get("key1");
        if (!"value1".equals(val)) {
            throw new Error("Expected value1 for key1, got: " + val);
        }

        // Test get returns null for key not in map
        String val2 = map.get("key2");
        if (val2 != null) {
            throw new Error("Expected null for key2 not in map");
        }
    }
}

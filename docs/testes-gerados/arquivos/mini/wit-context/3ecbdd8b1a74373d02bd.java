/*
 * @test
 * @summary Test ConcurrentHashMap.put(K,V) for null key or value throwing NullPointerException
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        // Test normal put
        String prev = map.put("key1", "value1");
        if (prev != null) {
            throw new AssertionError("Expected null on first put");
        }
        String val = map.get("key1");
        if (!"value1".equals(val)) {
            throw new AssertionError("Expected value1 after put");
        }

        // Test put with null key - expect NullPointerException
        try {
            map.put(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }

        // Test put with null value - expect NullPointerException
        try {
            map.put("key2", null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
        }

        // Test put replacing existing value
        prev = map.put("key1", "value2");
        if (!"value1".equals(prev)) {
            throw new AssertionError("Expected previous value1 on replace");
        }
        val = map.get("key1");
        if (!"value2".equals(val)) {
            throw new AssertionError("Expected value2 after replace");
        }
    }
}

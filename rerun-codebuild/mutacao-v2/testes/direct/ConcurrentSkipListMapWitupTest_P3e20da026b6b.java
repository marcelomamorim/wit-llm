/*
 * @test
 * @summary Test ConcurrentSkipListMap.put(K,V) method behavior
 * @run main ConcurrentSkipListMapWitupTest_P3e20da026b6b
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P3e20da026b6b {
    public static void main(String[] args) {
        testPutNullValueThrows();
        testPutAndGet();
    }

    private static void testPutNullValueThrows() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.put("key", null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testPutAndGet() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String prev = map.put("key1", "value1");
        if (prev != null) {
            throw new Error("Expected previous value to be null");
        }
        String val = map.get("key1");
        if (!"value1".equals(val)) {
            throw new Error("Expected value1 but got " + val);
        }

        // Overwrite existing key
        prev = map.put("key1", "value2");
        if (!"value1".equals(prev)) {
            throw new Error("Expected previous value to be value1");
        }
        val = map.get("key1");
        if (!"value2".equals(val)) {
            throw new Error("Expected value2 but got " + val);
        }
    }
}

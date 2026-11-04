/*
 * @test
 * @summary Test ConcurrentSkipListMap.putIfAbsent behavior including null key rejection
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testPutIfAbsentNullKey();
        testPutIfAbsentBasic();
    }

    private static void testPutIfAbsentNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.putIfAbsent(null, "value");
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testPutIfAbsentBasic() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String prev = map.putIfAbsent("key1", "value1");
        if (prev != null) {
            throw new Error("Expected null return on first putIfAbsent");
        }
        // putIfAbsent should not overwrite existing value
        prev = map.putIfAbsent("key1", "value2");
        if (!"value1".equals(prev)) {
            throw new Error("Expected previous value 'value1' on putIfAbsent");
        }
        // Confirm value is unchanged
        String val = map.get("key1");
        if (!"value1".equals(val)) {
            throw new Error("Expected value 'value1' after putIfAbsent");
        }
    }
}

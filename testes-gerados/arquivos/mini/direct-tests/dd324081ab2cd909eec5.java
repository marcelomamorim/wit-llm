/*
 * @test
 * @summary Test ConcurrentSkipListMap.replace(K,V) method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testReplaceNullKey();
        testReplaceNullValue();
        testReplaceNonExistentKey();
        testReplaceExistingKey();
    }

    static void testReplaceNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replace(null, "value");
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }
    }

    static void testReplaceNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replace("key", null);
            throw new Error("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
        }
    }

    static void testReplaceNonExistentKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String result = map.replace("absent", "newValue");
        if (result != null) {
            throw new Error("Expected null return when replacing non-existent key");
        }
    }

    static void testReplaceExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "oldValue");
        String oldVal = map.replace("key", "newValue");
        if (!"oldValue".equals(oldVal)) {
            throw new Error("Expected old value to be returned on replace");
        }
        String currentVal = map.get("key");
        if (!"newValue".equals(currentVal)) {
            throw new Error("Expected new value to be set on replace");
        }
    }
}

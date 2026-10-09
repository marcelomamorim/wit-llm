/*
 * @test
 * @summary Test ConcurrentSkipListMap.replace(K key, V oldValue, V newValue) method
 * @run main ConcurrentSkipListMapWitupTest_Pbbb179a17783
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Pbbb179a17783 {
    public static void main(String[] args) {
        testReplaceNullKeyThrows();
        testReplaceNullOldValueThrows();
        testReplaceNullNewValueThrows();
        testReplaceNonExistentKeyReturnsFalse();
        testReplaceOldValueMismatchReturnsFalse();
        testReplaceSuccessfulReturnsTrue();
    }

    private static void testReplaceNullKeyThrows() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        try {
            map.replace(null, "value1", "value2");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }
    }

    private static void testReplaceNullOldValueThrows() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        try {
            map.replace("key1", null, "value2");
            throw new RuntimeException("Expected NullPointerException for null oldValue");
        } catch (NullPointerException expected) {
        }
    }

    private static void testReplaceNullNewValueThrows() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        try {
            map.replace("key1", "value1", null);
            throw new RuntimeException("Expected NullPointerException for null newValue");
        } catch (NullPointerException expected) {
        }
    }

    private static void testReplaceNonExistentKeyReturnsFalse() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean replaced = map.replace("nonexistent", "old", "new");
        if (replaced) {
            throw new RuntimeException("Expected replace to return false for non-existent key");
        }
    }

    private static void testReplaceOldValueMismatchReturnsFalse() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        boolean replaced = map.replace("key1", "wrongOldValue", "newValue");
        if (replaced) {
            throw new RuntimeException("Expected replace to return false when oldValue does not match");
        }
    }

    private static void testReplaceSuccessfulReturnsTrue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        boolean replaced = map.replace("key1", "value1", "value2");
        if (!replaced) {
            throw new RuntimeException("Expected replace to return true when replacement succeeds");
        }
        String val = map.get("key1");
        if (!"value2".equals(val)) {
            throw new RuntimeException("Expected value to be updated to 'value2'");
        }
    }
}

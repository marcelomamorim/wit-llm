/*
 * @test
 * @summary Test ConcurrentHashMap.replace(K key, V oldValue, V newValue) method
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testReplaceSuccess();
        testReplaceFailOnOldValueMismatch();
        testReplaceNullKey();
        testReplaceNullOldValue();
        testReplaceNullNewValue();
    }

    private static void testReplaceSuccess() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        boolean replaced = map.replace("key1", "value1", "value2");
        if (!replaced) throw new RuntimeException("replace should return true when oldValue matches");
        String val = map.get("key1");
        if (!"value2".equals(val)) throw new RuntimeException("value should be replaced with newValue");
    }

    private static void testReplaceFailOnOldValueMismatch() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key2", "value1");
        boolean replaced = map.replace("key2", "wrongOldValue", "value2");
        if (replaced) throw new RuntimeException("replace should return false when oldValue does not match");
        String val = map.get("key2");
        if (!"value1".equals(val)) throw new RuntimeException("value should not be replaced if oldValue does not match");
    }

    private static void testReplaceNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace(null, "old", "new");
            throw new RuntimeException("replace should throw NullPointerException if key is null");
        } catch (NullPointerException expected) {
        }
    }

    private static void testReplaceNullOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace("key", null, "new");
            throw new RuntimeException("replace should throw NullPointerException if oldValue is null");
        } catch (NullPointerException expected) {
        }
    }

    private static void testReplaceNullNewValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace("key", "old", null);
            throw new RuntimeException("replace should throw NullPointerException if newValue is null");
        } catch (NullPointerException expected) {
        }
    }
}

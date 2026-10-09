/*
 * @test
 * @summary Test ConcurrentHashMap.replace(K key, V oldValue, V newValue) for null checks and correct replacement
 * @run main ConcurrentHashMapWitupTest_P98f5ee332856
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_P98f5ee332856 {
    public static void main(String[] args) {
        testReplaceNullKey();
        testReplaceNullOldValue();
        testReplaceNullNewValue();
        testReplaceSuccessful();
        testReplaceUnsuccessful();
    }

    private static void testReplaceNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace(null, "old", "new");
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }
    }

    private static void testReplaceNullOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace("key", null, "new");
            throw new Error("Expected NullPointerException for null oldValue");
        } catch (NullPointerException expected) {
        }
    }

    private static void testReplaceNullNewValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace("key", "old", null);
            throw new Error("Expected NullPointerException for null newValue");
        } catch (NullPointerException expected) {
        }
    }

    private static void testReplaceSuccessful() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "old");
        boolean replaced = map.replace("key", "old", "new");
        if (!replaced) {
            throw new Error("Expected replace to return true when oldValue matches");
        }
        String val = map.get("key");
        if (!"new".equals(val)) {
            throw new Error("Expected value to be updated to 'new'");
        }
    }

    private static void testReplaceUnsuccessful() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "old");
        boolean replaced = map.replace("key", "wrongOld", "new");
        if (replaced) {
            throw new Error("Expected replace to return false when oldValue does not match");
        }
        String val = map.get("key");
        if (!"old".equals(val)) {
            throw new Error("Expected value to remain 'old' when replace fails");
        }
    }
}

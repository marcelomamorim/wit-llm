/* @test
   @summary Tests the replace method of ConcurrentHashMap for various scenarios.
   @run main ConcurrentHashMapWitupTest_P9b9ded78feec
 */
import java.util.concurrent.ConcurrentHashMap;
public class ConcurrentHashMapWitupTest_P9b9ded78feec {
    public static void main(String[] args) {
        testReplaceWithNulls();
        testReplaceWithNonExistingKey();
        testReplaceWithExistingKeyAndMatchingOldValue();
        testReplaceWithExistingKeyAndNonMatchingOldValue();
    }

    static void testReplaceWithNulls() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace(null, "old", "new");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) { /* expected */ }
        try {
            map.replace("key", null, "new");
            throw new AssertionError("Expected NullPointerException for null oldValue");
        } catch (NullPointerException expected) { /* expected */ }
        try {
            map.replace("key", "old", null);
            throw new AssertionError("Expected NullPointerException for null newValue");
        } catch (NullPointerException expected) { /* expected */ }
    }

    static void testReplaceWithNonExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean result = map.replace("nonexistent", "old", "new");
        if (result) {
            throw new AssertionError("Replace should return false for non-existing key");
        }
    }

    static void testReplaceWithExistingKeyAndMatchingOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        boolean result = map.replace("key", "value", "newValue");
        if (!result) {
            throw new AssertionError("Replace should return true when oldValue matches");
        }
        if (!"newValue".equals(map.get("key"))) {
            throw new AssertionError("Value not replaced correctly");
        }
    }

    static void testReplaceWithExistingKeyAndNonMatchingOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        boolean result = map.replace("key", "wrongOld", "newValue");
        if (result) {
            throw new AssertionError("Replace should return false when oldValue does not match");
        }
        if (!"value".equals(map.get("key"))) {
            throw new AssertionError("Value should not be replaced when oldValue does not match");
        }
    }
}

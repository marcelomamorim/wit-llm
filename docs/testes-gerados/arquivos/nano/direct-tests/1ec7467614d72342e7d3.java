/*
 * @test    @summary Test the replace method of ConcurrentHashMap for correct behavior.
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testReplaceWithNulls();
        testReplaceWithNonExistingKey();
        testReplaceWithExistingKeyAndMatchingOldValue();
        testReplaceWithExistingKeyAndNonMatchingOldValue();
    }

    static void testReplaceWithNulls() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean exceptionThrown = false;
        try {
            map.replace(null, "old", "new");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException for null key";
        exceptionThrown = false;
        try {
            map.replace("key", null, "new");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException for null oldValue";
        exceptionThrown = false;
        try {
            map.replace("key", "old", null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException for null newValue";
    }

    static void testReplaceWithNonExistingKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean result = map.replace("nonexistent", "old", "new");
        assert !result : "Replace should return false for non-existing key";
    }

    static void testReplaceWithExistingKeyAndMatchingOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "oldValue");
        boolean result = map.replace("key", "oldValue", "newValue");
        assert result : "Replace should return true when oldValue matches";
        assert "newValue".equals(map.get("key")) : "Value should be updated to newValue";
    }

    static void testReplaceWithExistingKeyAndNonMatchingOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "currentValue");
        boolean result = map.replace("key", "oldValue", "newValue");
        assert !result : "Replace should return false when oldValue does not match";
        assert "currentValue".equals(map.get("key")) : "Value should remain unchanged";
    }
}

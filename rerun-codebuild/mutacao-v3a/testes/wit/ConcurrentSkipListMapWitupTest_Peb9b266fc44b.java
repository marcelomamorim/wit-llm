/*
 * @test
 * @summary Tests for ConcurrentSkipListMap.replace(K key, V oldValue, V newValue)
 * @run main ConcurrentSkipListMapWitupTest_Peb9b266fc44b
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest_Peb9b266fc44b {

    public static void main(String[] args) {
        testReplace_NullKeysAndValues();
        testReplace_KeyNotFound();
        testReplace_OldValueMismatch();
        testReplace_Successful();
    }

    // Test that replace throws NullPointerException if key is null
    static void testReplace_NullKeysAndValues() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
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

    // Test that replace returns false if key not present
    static void testReplace_KeyNotFound() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean result = map.replace("nonexistent", "old", "new");
        assert result == false : "Replace should return false if key not found";
    }

    // Test that replace returns false if oldValue does not match current value
    static void testReplace_OldValueMismatch() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "value");
        boolean result = map.replace("key", "wrongOld", "newValue");
        assert result == false : "Replace should return false if oldValue does not match";
    }

    // Test that replace successfully updates value when conditions are met
    static void testReplace_Successful() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "oldValue");
        boolean result = map.replace("key", "oldValue", "newValue");
        assert result == true : "Replace should succeed when oldValue matches";
        String newVal = map.get("key");
        assert "newValue".equals(newVal) : "Value should be updated to newValue";
    }
}

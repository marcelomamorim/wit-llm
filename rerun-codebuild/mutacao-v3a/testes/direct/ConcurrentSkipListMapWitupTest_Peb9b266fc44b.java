/*
 * @test    @summary Test replace method in ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_Peb9b266fc44b
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Peb9b266fc44b {

    public static void main(String[] args) {
        testReplaceMethod();
    }

    public static void testReplaceMethod() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "testKey";
        String initialValue = "initial";
        String newValue = "new";
        String wrongOldValue = "wrong";

        // Insert initial key-value pair
        map.put(key, initialValue);

        // Test case: correct old value, should replace
        boolean replaced = map.replace(key, initialValue, newValue);
        if (!replaced) {
            throw new AssertionError("Expected replace to succeed with correct old value");
        }
        if (!map.get(key).equals(newValue)) {
            throw new AssertionError("Value not updated after replace");
        }

        // Reset value
        map.put(key, initialValue);

        // Test case: wrong old value, should not replace
        boolean replacedWrong = map.replace(key, wrongOldValue, newValue);
        if (replacedWrong) {
            throw new AssertionError("Replace should fail with incorrect old value");
        }
        if (!map.get(key).equals(initialValue)) {
            throw new AssertionError("Value should not change when replace fails");
        }

        // Test case: null key
        try {
            map.replace(null, initialValue, newValue);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test case: null oldValue
        try {
            map.replace(key, null, newValue);
            throw new AssertionError("Expected NullPointerException for null oldValue");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test case: null newValue
        try {
            map.replace(key, initialValue, null);
            throw new AssertionError("Expected NullPointerException for null newValue");
        } catch (NullPointerException expected) {
            // expected
        }

        System.out.println("All test cases passed for replace method.");
    }
}

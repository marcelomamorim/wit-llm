/*
 * @test
 * @summary Test java.util.Hashtable.replace(K key, V oldValue, V newValue) method
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Objects;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testReplaceNullOldValue();
        testReplaceNullNewValueWithNonNullOldValue();
        testReplaceKeyNotPresent();
        testReplaceOldValueMismatch();
        testReplaceSuccessful();
    }

    private static void testReplaceNullOldValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        try {
            ht.replace("key", null, "newValue");
            throw new RuntimeException("Expected NullPointerException for null oldValue");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceNullNewValueWithNonNullOldValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        try {
            ht.replace("key", "value", null);
            throw new RuntimeException("Expected NullPointerException for null newValue when oldValue non-null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceKeyNotPresent() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        boolean result = ht.replace("absentKey", "value", "newValue");
        if (result) {
            throw new RuntimeException("Expected false when key not present");
        }
    }

    private static void testReplaceOldValueMismatch() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        boolean result = ht.replace("key", "wrongOldValue", "newValue");
        if (result) {
            throw new RuntimeException("Expected false when oldValue does not match current value");
        }
        // Confirm value unchanged
        String val = ht.get("key");
        if (!"value".equals(val)) {
            throw new RuntimeException("Value changed unexpectedly when oldValue mismatched");
        }
    }

    private static void testReplaceSuccessful() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        boolean result = ht.replace("key", "value", "newValue");
        if (!result) {
            throw new RuntimeException("Expected true when replace successful");
        }
        String val = ht.get("key");
        if (!"newValue".equals(val)) {
            throw new RuntimeException("Value not updated correctly after replace");
        }
    }
}

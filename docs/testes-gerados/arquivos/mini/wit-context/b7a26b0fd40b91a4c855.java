/*
 * @test
 * @summary Test java.util.Hashtable.remove(Object key, Object value) method behavior
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Objects;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testRemoveWithNullValueThrowsNPE();
        testRemoveExistingEntryReturnsTrue();
        testRemoveNonExistingEntryReturnsFalse();
    }

    private static void testRemoveWithNullValueThrowsNPE() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        try {
            ht.remove("key", null);
            throw new AssertionError("Expected NullPointerException when value is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveExistingEntryReturnsTrue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        boolean removed = ht.remove("key", "value");
        if (!removed) {
            throw new AssertionError("Expected remove to return true for existing key-value pair");
        }
        if (ht.containsKey("key")) {
            throw new AssertionError("Expected key to be removed from hashtable");
        }
    }

    private static void testRemoveNonExistingEntryReturnsFalse() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        boolean removed = ht.remove("key", "otherValue");
        if (removed) {
            throw new AssertionError("Expected remove to return false for non-matching value");
        }
        if (!ht.containsKey("key")) {
            throw new AssertionError("Expected key to remain in hashtable when value does not match");
        }
    }
}

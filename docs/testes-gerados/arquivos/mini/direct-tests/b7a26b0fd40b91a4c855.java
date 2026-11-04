/*
 * @test
 * @summary Test java.util.Hashtable remove(Object key, Object value) method
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Objects;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testRemoveExistingEntry();
        testRemoveNonExistingEntry();
        testRemoveNullValueThrows();
        testRemoveWithDifferentValue();
    }

    private static void testRemoveExistingEntry() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        boolean removed = ht.remove("key1", "value1");
        if (!removed) {
            throw new AssertionError("Expected remove to return true for existing key-value pair");
        }
        if (ht.containsKey("key1")) {
            throw new AssertionError("Hashtable should not contain key after removal");
        }
    }

    private static void testRemoveNonExistingEntry() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        boolean removed = ht.remove("key2", "value1");
        if (removed) {
            throw new AssertionError("Expected remove to return false for non-existing key");
        }
        if (!ht.containsKey("key1")) {
            throw new AssertionError("Hashtable should still contain original key");
        }
    }

    private static void testRemoveNullValueThrows() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        try {
            ht.remove("key1", null);
            throw new AssertionError("Expected NullPointerException when removing with null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveWithDifferentValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        boolean removed = ht.remove("key1", "value2");
        if (removed) {
            throw new AssertionError("Expected remove to return false when value does not match");
        }
        if (!ht.containsKey("key1")) {
            throw new AssertionError("Hashtable should still contain key when value does not match");
        }
    }
}

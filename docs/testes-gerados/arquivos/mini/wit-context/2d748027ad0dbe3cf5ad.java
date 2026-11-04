/*
 * @test
 * @summary Test java.util.Hashtable.replace(K key, V value) method
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Objects;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testReplaceWithNullValueThrowsNPE();
        testReplaceExistingKeyReturnsOldValue();
        testReplaceNonExistingKeyReturnsNull();
    }

    private static void testReplaceWithNullValueThrowsNPE() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        try {
            ht.replace("key1", null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testReplaceExistingKeyReturnsOldValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        String oldValue = ht.replace("key1", "value2");
        if (!"value1".equals(oldValue)) {
            throw new RuntimeException("replace did not return old value for existing key");
        }
        String newValue = ht.get("key1");
        if (!"value2".equals(newValue)) {
            throw new RuntimeException("replace did not update value for existing key");
        }
    }

    private static void testReplaceNonExistingKeyReturnsNull() {
        Hashtable<String, String> ht = new Hashtable<>();
        String result = ht.replace("nonexistent", "value");
        if (result != null) {
            throw new RuntimeException("replace did not return null for non-existing key");
        }
        if (!"value".equals(ht.get("nonexistent"))) {
            throw new RuntimeException("replace did not insert value for non-existing key");
        }
    }
}

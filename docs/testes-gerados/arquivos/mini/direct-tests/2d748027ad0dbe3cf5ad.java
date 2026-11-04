/*
 * @test
 * @summary Test java.util.Hashtable.replace(K key, V value) method behavior
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Objects;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testReplaceExistingKey();
        testReplaceNonExistingKey();
        testReplaceNullValueThrows();
    }

    private static void testReplaceExistingKey() {
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

    private static void testReplaceNonExistingKey() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        String oldValue = ht.replace("key2", "value2");
        if (oldValue != null) {
            throw new RuntimeException("replace returned non-null for non-existing key");
        }
        if (ht.containsKey("key2")) {
            throw new RuntimeException("replace added new key instead of replacing");
        }
    }

    private static void testReplaceNullValueThrows() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        try {
            ht.replace("key1", null);
            throw new RuntimeException("replace did not throw NullPointerException when value is null");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}

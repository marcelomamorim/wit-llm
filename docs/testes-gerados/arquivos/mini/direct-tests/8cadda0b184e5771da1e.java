/*
 * @test
 * @summary Test java.util.Hashtable.replace(K key, V oldValue, V newValue) method
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Objects;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testReplaceSuccess();
        testReplaceFailWrongOldValue();
        testReplaceFailNoKey();
        testReplaceNullOldValue();
        testReplaceNullNewValue();
    }

    private static void testReplaceSuccess() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        boolean replaced = ht.replace("key1", "value1", "value2");
        if (!replaced) {
            throw new RuntimeException("replace should return true when oldValue matches");
        }
        String val = ht.get("key1");
        if (!"value2".equals(val)) {
            throw new RuntimeException("value should be replaced with newValue");
        }
    }

    private static void testReplaceFailWrongOldValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key2", "value1");
        boolean replaced = ht.replace("key2", "wrongOldValue", "value2");
        if (replaced) {
            throw new RuntimeException("replace should return false when oldValue does not match");
        }
        String val = ht.get("key2");
        if (!"value1".equals(val)) {
            throw new RuntimeException("value should not be replaced when oldValue does not match");
        }
    }

    private static void testReplaceFailNoKey() {
        Hashtable<String, String> ht = new Hashtable<>();
        boolean replaced = ht.replace("nonexistentKey", "value1", "value2");
        if (replaced) {
            throw new RuntimeException("replace should return false when key is not present");
        }
    }

    private static void testReplaceNullOldValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key3", "value3");
        try {
            ht.replace("key3", null, "value4");
            throw new RuntimeException("replace should throw NullPointerException when oldValue is null");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testReplaceNullNewValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key4", "value4");
        try {
            ht.replace("key4", "value4", null);
            throw new RuntimeException("replace should throw NullPointerException when newValue is null");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}

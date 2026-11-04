/*
 * @test
 * @summary Basic tests for java.util.Hashtable.put method
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testPutNewEntryReturnsNull();
        testPutReplaceReturnsOldValue();
        testPutNullValueThrowsNPE();
        testPutWithNullKeyAllowed();
    }

    private static void testPutNewEntryReturnsNull() {
        Hashtable<String, String> ht = new Hashtable<>();
        String ret = ht.put("key1", "value1");
        if (ret != null) {
            throw new RuntimeException("Expected null when putting new entry, got: " + ret);
        }
        if (!"value1".equals(ht.get("key1"))) {
            throw new RuntimeException("Value not stored correctly for key1");
        }
    }

    private static void testPutReplaceReturnsOldValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key2", "value2");
        String old = ht.put("key2", "value3");
        if (!"value2".equals(old)) {
            throw new RuntimeException("Expected old value 'value2' when replacing, got: " + old);
        }
        if (!"value3".equals(ht.get("key2"))) {
            throw new RuntimeException("Value not replaced correctly for key2");
        }
    }

    private static void testPutNullValueThrowsNPE() {
        Hashtable<String, String> ht = new Hashtable<>();
        try {
            ht.put("key3", null);
            throw new RuntimeException("Expected NullPointerException when putting null value");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testPutWithNullKeyAllowed() {
        Hashtable<String, String> ht = new Hashtable<>();
        try {
            ht.put(null, "value4");
            // In Hashtable, null keys are not allowed and should throw NullPointerException
            throw new RuntimeException("Expected NullPointerException when putting null key");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}

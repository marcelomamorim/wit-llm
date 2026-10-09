/*
 * @test
 * @summary Deterministic tests for java.util.Hashtable.put(K key, V value) method
 * @run main HashtableWitupTest_Pf9879969b6ea
 */

import java.util.Hashtable;

public class HashtableWitupTest_Pf9879969b6ea {
    public static void main(String[] args) {
        testPutNullValueThrowsNPE();
        testPutNewKeyReturnsNull();
        testPutExistingKeyReturnsOldValue();
    }

    private static void testPutNullValueThrowsNPE() {
        Hashtable<String, String> ht = new Hashtable<>();
        try {
            ht.put("key", null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testPutNewKeyReturnsNull() {
        Hashtable<String, String> ht = new Hashtable<>();
        String oldValue = ht.put("key1", "value1");
        if (oldValue != null) {
            throw new RuntimeException("Expected null when putting new key, got: " + oldValue);
        }
    }

    private static void testPutExistingKeyReturnsOldValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        String oldValue = ht.put("key1", "value2");
        if (!"value1".equals(oldValue)) {
            throw new RuntimeException("Expected old value 'value1' when replacing, got: " + oldValue);
        }
        String currentValue = ht.get("key1");
        if (!"value2".equals(currentValue)) {
            throw new RuntimeException("Expected current value 'value2' after replacement, got: " + currentValue);
        }
    }
}

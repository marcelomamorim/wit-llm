/* @test    @summary Test Hashtable.remove(Object key, Object value) for correct removal behavior    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testRemoveExistingKeyValue();
        testRemoveNonExistingKey();
        testRemoveWithNullValue();
        testRemoveWithNullKey();
    }

    private static void testRemoveExistingKeyValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        boolean removed = table.remove("key1", "value1");
        assert removed : "Expected removal of existing key-value pair to return true";
        assert !table.containsKey("key1") : "Key should be removed from the table";
    }

    private static void testRemoveNonExistingKey() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key2", "value2");
        boolean removed = table.remove("nonexistent", "value2");
        assert !removed : "Expected removal of non-existing key to return false";
        assert table.containsKey("key2") : "Existing key should remain in the table";
    }

    private static void testRemoveWithNullValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key3", "value3");
        try {
            table.remove("key3", null);
            assert false : "Expected NullPointerException when value is null";
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testRemoveWithNullKey() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key4", "value4");
        try {
            table.remove(null, "value4");
            assert false : "Expected NullPointerException when key is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}

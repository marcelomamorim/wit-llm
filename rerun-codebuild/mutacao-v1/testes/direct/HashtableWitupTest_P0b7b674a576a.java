/* @test    @summary Test for Hashtable.put method to ensure null values throw NullPointerException and insertion works correctly    @run main HashtableWitupTest_P0b7b674a576a */

import java.util.Hashtable;

public class HashtableWitupTest_P0b7b674a576a {

    public static void main(String[] args) {
        testPutWithNullValue();
        testPutWithValidEntries();
    }

    private static void testPutWithNullValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        boolean exceptionThrown = false;
        try {
            ht.put("key", null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when putting null value");
        }
    }

    private static void testPutWithValidEntries() {
        Hashtable<String, String> ht = new Hashtable<>();
        String oldValue = ht.put("a", "1");
        if (oldValue != null) {
            throw new AssertionError("Expected null as old value for new key");
        }
        String retrieved = ht.get("a");
        if (!"1".equals(retrieved)) {
            throw new AssertionError("Value retrieved does not match inserted value");
        }
        // Update existing key
        String previous = ht.put("a", "2");
        if (!"1".equals(previous)) {
            throw new AssertionError("Previous value should be '1'");
        }
        String newVal = ht.get("a");
        if (!"2".equals(newVal)) {
            throw new AssertionError("Updated value should be '2'");
        }
    }
}

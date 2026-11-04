/* @test    @summary Test the replace method of Hashtable for correct replacement when oldValue matches.    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testReplaceSuccess();
        testReplaceOldValueMismatch();
        testReplaceNullOldValue();
        testReplaceNullNewValue();
    }

    private static void testReplaceSuccess() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        boolean replaced = table.replace("key1", "value1", "newValue1");
        if (!replaced) {
            throw new AssertionError("Expected replace to return true");
        }
        String result = table.get("key1");
        if (!"newValue1".equals(result)) {
            throw new AssertionError("Expected value to be 'newValue1', but was: " + result);
        }
    }

    private static void testReplaceOldValueMismatch() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key2", "value2");
        boolean replaced = table.replace("key2", "wrongOldValue", "newValue2");
        if (replaced) {
            throw new AssertionError("Expected replace to return false when oldValue does not match");
        }
        String result = table.get("key2");
        if (!"value2".equals(result)) {
            throw new AssertionError("Expected value to remain 'value2', but was: " + result);
        }
    }

    private static void testReplaceNullOldValue() {
        try {
            Hashtable<String, String> table = new Hashtable<>();
            table.put("key3", "value3");
            table.replace("key3", null, "newValue3");
            throw new AssertionError("Expected NullPointerException for null oldValue");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceNullNewValue() {
        try {
            Hashtable<String, String> table = new Hashtable<>();
            table.put("key4", "value4");
            table.replace("key4", "value4", null);
            throw new AssertionError("Expected NullPointerException for null newValue");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

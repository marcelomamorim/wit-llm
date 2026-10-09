/* @test    @summary Test the contains method of Hashtable for various values    @run main HashtableWitupTest_Pe48ebb1ba6cb */

import java.util.Hashtable;

public class HashtableWitupTest_Pe48ebb1ba6cb {

    public static void main(String[] args) {
        testContainsWithExistingValue();
        testContainsWithNonExistingValue();
        testContainsWithNullValue();
    }

    public static void testContainsWithExistingValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        boolean result = table.contains("value1");
        if (!result) {
            throw new AssertionError("Expected contains to return true for existing value");
        }
    }

    public static void testContainsWithNonExistingValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        boolean result = table.contains("value2");
        if (result) {
            throw new AssertionError("Expected contains to return false for non-existing value");
        }
    }

    public static void testContainsWithNullValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        try {
            table.contains(null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException e) {
            // expected
        }
    }
}

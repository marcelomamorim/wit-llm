/*
 * @test
 * @summary Test java.util.Hashtable.contains(Object) method
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testContainsWithNonNullValue();
        testContainsWithNullValue();
    }

    private static void testContainsWithNonNullValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");
        ht.put("key2", "value2");

        // The contains method should return true if the value is present
        if (!ht.contains("value1")) {
            throw new AssertionError("Expected contains to return true for existing value 'value1'");
        }

        // The contains method should return false if the value is not present
        if (ht.contains("value3")) {
            throw new AssertionError("Expected contains to return false for non-existing value 'value3'");
        }
    }

    private static void testContainsWithNullValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key1", "value1");

        // According to the source and WIT, contains(null) should throw NullPointerException
        try {
            ht.contains(null);
            throw new AssertionError("Expected NullPointerException when calling contains(null)");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}

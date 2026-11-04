/*
 * @test
 * @summary Tests the contains method of java.util.Hashtable for null input.
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithExistingValue();
        testContainsWithNonExistingValue();
    }

    private static void testContainsWithNull() {
        Hashtable<Object, Object> table = new Hashtable<>();
        boolean exceptionThrown = false;
        try {
            table.contains(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when calling contains(null)");
        }
    }

    private static void testContainsWithExistingValue() {
        Hashtable<String, String> table = new Hashtable<>();
        String key = "key";
        String value = "value";
        table.put(key, value);
        boolean result = table.contains(value);
        if (!result) {
            throw new AssertionError("contains should return true for existing value")
        }
    }

    private static void testContainsWithNonExistingValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("a", "b");
        boolean result = table.contains("nonexistent");
        if (result) {
            throw new AssertionError("contains should return false for non-existing value")
        }
    }
}

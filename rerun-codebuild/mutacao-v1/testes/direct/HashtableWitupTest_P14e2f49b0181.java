/* @test    @summary Test for Hashtable.contains(Object value) method    @run main HashtableWitupTest_P14e2f49b0181 */

import java.util.Hashtable;

public class HashtableWitupTest_P14e2f49b0181 {
    public static void main(String[] args) {
        testContainsWithExistingValue();
        testContainsWithNonExistingValue();
        testContainsWithNullThrows();
    }

    private static void testContainsWithExistingValue() {
        Hashtable<String, String> table = new Hashtable<>();
        String key = "key1";
        String value = "value1";
        table.put(key, value);
        boolean result = table.contains(value);
        if (!result) {
            throw new AssertionError("Expected contains to return true for existing value") ;
        }
    }

    private static void testContainsWithNonExistingValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        boolean result = table.contains("nonexistent");
        if (result) {
            throw new AssertionError("Expected contains to return false for non-existing value") ;
        }
    }

    private static void testContainsWithNullThrows() {
        Hashtable<String, String> table = new Hashtable<>();
        boolean threw = false;
        try {
            table.contains(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when calling contains(null)") ;
        }
    }
}

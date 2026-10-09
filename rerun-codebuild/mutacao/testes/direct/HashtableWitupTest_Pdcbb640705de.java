/* @test    @summary Test for Hashtable.contains(Object value) method    @run main HashtableWitupTest_Pdcbb640705de */

import java.util.Hashtable;

public class HashtableWitupTest_Pdcbb640705de {

    public static void main(String[] args) {
        testContainsWithExistingValue();
        testContainsWithNonExistingValue();
        testContainsWithNullThrows();
    }

    static void testContainsWithExistingValue() {
        Hashtable<String, String> table = new Hashtable<>();
        String value = "testValue";
        table.put("key1", value);
        boolean result = table.contains(value);
        if (!result) {
            throw new AssertionError("Expected contains to return true for existing value");
        }
    }

    static void testContainsWithNonExistingValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        boolean result = table.contains("nonExistingValue");
        if (result) {
            throw new AssertionError("Expected contains to return false for non-existing value");
        }
    }

    static void testContainsWithNullThrows() {
        Hashtable<String, String> table = new Hashtable<>();
        boolean threw = false;
        try {
            table.contains(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when calling contains(null)");
        }
    }
}

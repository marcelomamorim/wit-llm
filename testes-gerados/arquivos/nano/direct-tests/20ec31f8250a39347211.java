/* @test    @summary Test for Hashtable.containsValue(Object value) method    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
        testContainsValueWithNull();
    }

    public static void testContainsValueWithExistingValue() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        table.put("two", 2);
        boolean result = table.containsValue(1);
        if (!result) {
            throw new AssertionError("Expected true for existing value 1");
        }
    }

    public static void testContainsValueWithNonExistingValue() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        table.put("two", 2);
        boolean result = table.containsValue(3);
        if (result) {
            throw new AssertionError("Expected false for non-existing value 3");
        }
    }

    public static void testContainsValueWithNull() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("null", null);
        boolean result = table.containsValue(null);
        if (!result) {
            throw new AssertionError("Expected true for null value");
        }
    }
}

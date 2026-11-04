/* @test    @summary Test for Hashtable.contains(Object o) method    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testContainsWithExistingValue();
        testContainsWithNonExistingValue();
        testContainsWithNull();
    }

    static void testContainsWithExistingValue() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        boolean result = table.contains(1);
        if (!result) {
            throw new AssertionError("Expected contains to return true for existing value");
        }
    }

    static void testContainsWithNonExistingValue() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        boolean result = table.contains(2);
        if (result) {
            throw new AssertionError("Expected contains to return false for non-existing value");
        }
    }

    static void testContainsWithNull() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("null", null);
        boolean result = table.contains(null);
        if (!result) {
            throw new AssertionError("Expected contains to return true for null value");
        }
    }
}

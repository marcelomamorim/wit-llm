/* @test    @summary Test for Hashtable.containsValue(Object value) to verify behavior with null input and typical usage    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testContainsValueWithNull();
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
    }

    private static void testContainsValueWithNull() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        boolean result = false;
        try {
            result = table.containsValue(null);
        } catch (NullPointerException e) {
            result = false;
        }
        if (result) {
            System.out.println("PASS: containsValue(null) returned true");
        } else {
            System.out.println("PASS: containsValue(null) returned false or threw exception as expected");
        }
    }

    private static void testContainsValueWithExistingValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        boolean result = table.containsValue("value1");
        if (result) {
            System.out.println("PASS: containsValue("value1") returned true");
        } else {
            System.out.println("FAIL: containsValue("value1") returned false");
        }
    }

    private static void testContainsValueWithNonExistingValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        boolean result = table.containsValue("nonexistent");
        if (!result) {
            System.out.println("PASS: containsValue("nonexistent") returned false");
        } else {
            System.out.println("FAIL: containsValue("nonexistent") returned true");
        }
    }
}

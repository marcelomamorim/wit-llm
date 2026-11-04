/* @test    @summary Test for Hashtable.contains(Object o) method to verify behavior with null input.    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithExistingObject();
        testContainsWithNonExistingObject();
    }

    private static void testContainsWithNull() {
        Hashtable<Object, Object> table = new Hashtable<>();
        Object key = null;
        boolean result = false;
        try {
            result = table.contains(key);
            System.out.println("contains(null) = " + result);
        } catch (NullPointerException e) {
            System.out.println("contains(null) threw NullPointerException as expected");
        }
    }

    private static void testContainsWithExistingObject() {
        Hashtable<String, String> table = new Hashtable<>();
        String key = "testKey";
        String value = "testValue";
        table.put(key, value);
        boolean result = table.contains(value);
        System.out.println("contains(value) for existing value = " + result);
    }

    private static void testContainsWithNonExistingObject() {
        Hashtable<String, String> table = new Hashtable<>();
        String value = "nonExisting";
        boolean result = table.contains(value);
        System.out.println("contains(value) for non-existing value = " + result);
    }
}

/*
 * @test
 * @summary Test for Properties.contains(Object) method.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithNonNull();
    }

    private static void testContainsWithNull() {
        Properties props = new Properties();
        boolean exceptionThrown = false;
        try {
            props.contains(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            System.out.println("FAIL: Expected NullPointerException when calling contains(null)");
        } else {
            System.out.println("PASS: contains(null) throws NullPointerException as expected");
        }
    }

    private static void testContainsWithNonNull() {
        Properties props = new Properties();
        String key = "testKey";
        String value = "testValue";
        props.put(key, value);
        boolean result = props.contains(value);
        if (result) {
            System.out.println("PASS: contains(value) returns true for existing value");
        } else {
            System.out.println("FAIL: contains(value) should return true for existing value");
        }
        // Test with a value not present
        boolean result2 = props.contains("nonexistentValue");
        if (!result2) {
            System.out.println("PASS: contains(nonexistentValue) returns false as expected");
        } else {
            System.out.println("FAIL: contains(nonexistentValue) should return false");
        }
    }
}

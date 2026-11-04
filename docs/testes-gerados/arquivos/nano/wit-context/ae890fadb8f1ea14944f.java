/*
 * @test
 * @summary Test for Properties.remove(Object, Object) method, focusing on null key behavior.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testRemoveWithNullKey();
        testRemoveWithNonNullKeyAndValue();
    }

    private static void testRemoveWithNullKey() {
        Properties props = new Properties();
        boolean exceptionThrown = false;
        try {
            props.remove(null, "someValue");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            System.out.println("Test failed: Expected NullPointerException when removing with null key.");
        } else {
            System.out.println("Test passed: NullPointerException thrown as expected for null key.");
        }
    }

    private static void testRemoveWithNonNullKeyAndValue() {
        Properties props = new Properties();
        String key = "testKey";
        String value = "testValue";
        props.setProperty(key, value);
        boolean result = props.remove(key, value);
        if (result && !props.containsKey(key)) {
            System.out.println("Test passed: remove(key, value) successfully removed the entry.");
        } else {
            System.out.println("Test failed: remove(key, value) did not behave as expected.");
        }
    }
}

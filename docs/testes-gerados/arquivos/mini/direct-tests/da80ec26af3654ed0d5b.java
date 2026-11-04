/*
 * @test
 * @summary Test java.util.Properties.contains(Object) method
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Initially empty, contains should return false
        if (props.contains("anyValue")) {
            throw new RuntimeException("Failed: contains should return false on empty Properties");
        }

        // Add a property and test contains with the value
        props.setProperty("key1", "value1");
        if (!props.contains("value1")) {
            throw new RuntimeException("Failed: contains should return true for existing value");
        }

        // Test contains with a value not present
        if (props.contains("value2")) {
            throw new RuntimeException("Failed: contains should return false for non-existing value");
        }

        // Test contains with null value (Properties allows null values via put, but setProperty disallows null)
        // Using put to insert null value
        props.put("key2", null);
        if (!props.contains(null)) {
            throw new RuntimeException("Failed: contains should return true for null value present");
        }

        // Test contains with an object that is not a String value
        Object obj = new Object();
        props.put("key3", obj);
        if (!props.contains(obj)) {
            throw new RuntimeException("Failed: contains should return true for non-String value present");
        }

        System.out.println("Properties.contains(Object) tests passed.");
    }
}

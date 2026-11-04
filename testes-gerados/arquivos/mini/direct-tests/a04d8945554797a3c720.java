/*
 * @test
 * @summary Test java.util.Properties.containsValue(Object) method
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Test containsValue on empty Properties returns false
        if (props.containsValue("anyValue")) {
            throw new RuntimeException("Failed: containsValue should return false on empty Properties");
        }

        // Add a property and test containsValue returns true for that value
        props.setProperty("key1", "value1");
        if (!props.containsValue("value1")) {
            throw new RuntimeException("Failed: containsValue should return true for existing value");
        }

        // Test containsValue returns false for a value not present
        if (props.containsValue("value2")) {
            throw new RuntimeException("Failed: containsValue should return false for non-existing value");
        }

        // Add a default Properties and test containsValue finds value in defaults
        Properties defaults = new Properties();
        defaults.setProperty("key2", "value2");
        Properties propsWithDefaults = new Properties(defaults);

        if (!propsWithDefaults.containsValue("value2")) {
            throw new RuntimeException("Failed: containsValue should return true for value in defaults");
        }

        // Test containsValue with null value (should return false as no null values)
        if (props.containsValue(null)) {
            throw new RuntimeException("Failed: containsValue should return false for null value when none present");
        }

        // Add a null value via put (discouraged but possible) and test containsValue
        props.put("keyNull", null);
        if (!props.containsValue(null)) {
            throw new RuntimeException("Failed: containsValue should return true for null value when present");
        }

        System.out.println("All tests passed.");
    }
}

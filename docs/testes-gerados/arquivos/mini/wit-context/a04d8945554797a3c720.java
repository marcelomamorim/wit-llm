/*
 * @test
 * @summary Test java.util.Properties.containsValue(Object) for normal and null input
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();
        // Add some properties
        props.setProperty("key1", "value1");
        props.setProperty("key2", "value2");

        // Test containsValue with existing value
        if (!props.containsValue("value1")) {
            throw new AssertionError("Expected containsValue to return true for existing value");
        }

        // Test containsValue with non-existing value
        if (props.containsValue("nonexistent")) {
            throw new AssertionError("Expected containsValue to return false for non-existing value");
        }

        // Test containsValue with null - expect NullPointerException
        try {
            props.containsValue(null);
            throw new AssertionError("Expected NullPointerException when calling containsValue with null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}

/* @test    @summary Test Properties constructor with initial capacity    @run main PropertiesWitupTest */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testPropertiesConstructorWithCapacity();
    }

    public static void testPropertiesConstructorWithCapacity() {
        int initialCapacity = 10;
        Properties props = new Properties(initialCapacity);
        if (props == null) {
            throw new AssertionError("Properties instance is null");
        }
        // Verify that the Properties object is empty initially
        if (props.size() != 0) {
            throw new AssertionError("Expected empty Properties, but size was: " + props.size());
        }
        // Add a property and verify
        String key = "testKey";
        String value = "testValue";
        props.setProperty(key, value);
        if (!props.getProperty(key).equals(value)) {
            throw new AssertionError("Property value mismatch: expected '" + value + "', got '" + props.getProperty(key) + "'");
        }
        // Verify size after insertion
        if (props.size() != 1) {
            throw new AssertionError("Expected size 1 after insertion, but got: " + props.size());
        }
    }
}

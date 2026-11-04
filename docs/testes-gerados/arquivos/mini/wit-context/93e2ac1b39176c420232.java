/*
 * @test
 * @summary Test java.util.Properties.replace(Object key, Object oldValue, Object newValue) for expected behavior and NPE
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Setup initial property
        props.setProperty("key1", "value1");

        // Test replace with correct oldValue
        boolean replaced = props.replace("key1", "value1", "value2");
        if (!replaced) {
            throw new RuntimeException("Expected replace to return true when oldValue matches");
        }
        if (!"value2".equals(props.getProperty("key1"))) {
            throw new RuntimeException("Expected value to be updated to 'value2'");
        }

        // Test replace with incorrect oldValue
        replaced = props.replace("key1", "wrongOldValue", "value3");
        if (replaced) {
            throw new RuntimeException("Expected replace to return false when oldValue does not match");
        }
        if (!"value2".equals(props.getProperty("key1"))) {
            throw new RuntimeException("Expected value to remain 'value2' when oldValue does not match");
        }

        // Test replace with null key - expect NullPointerException
        try {
            props.replace(null, "value2", "value3");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }

        // Test replace with null oldValue - expect NullPointerException
        try {
            props.replace("key1", null, "value3");
            throw new RuntimeException("Expected NullPointerException for null oldValue");
        } catch (NullPointerException expected) {
        }

        // Test replace with null newValue - expect NullPointerException
        try {
            props.replace("key1", "value2", null);
            throw new RuntimeException("Expected NullPointerException for null newValue");
        } catch (NullPointerException expected) {
        }
    }
}

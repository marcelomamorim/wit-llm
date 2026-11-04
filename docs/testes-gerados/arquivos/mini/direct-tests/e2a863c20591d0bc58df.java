/*
 * @test
 * @summary Test java.util.Properties.setProperty method basic behavior
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Test setting a property returns previous value or null if none
        Object ret1 = props.setProperty("key1", "value1");
        if (ret1 != null) {
            throw new RuntimeException("Expected null on first setProperty call");
        }

        // Test that the property is actually set
        String val = props.getProperty("key1");
        if (!"value1".equals(val)) {
            throw new RuntimeException("Property value mismatch after setProperty");
        }

        // Test overwriting existing property returns old value
        Object ret2 = props.setProperty("key1", "value2");
        if (!"value1".equals(ret2)) {
            throw new RuntimeException("Expected previous value on overwrite");
        }

        // Test that the property is updated
        String val2 = props.getProperty("key1");
        if (!"value2".equals(val2)) {
            throw new RuntimeException("Property value mismatch after overwrite");
        }

        // Test setting property with empty string key and value
        Object ret3 = props.setProperty("", "");
        if (ret3 != null) {
            throw new RuntimeException("Expected null when setting empty key first time");
        }
        String val3 = props.getProperty("");
        if (!"".equals(val3)) {
            throw new RuntimeException("Property value mismatch for empty key");
        }

        // Test setting property with null key or value throws NullPointerException
        // According to Hashtable.put contract, null keys or values are not allowed
        boolean npeThrown = false;
        try {
            props.setProperty(null, "value");
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null key");
        }

        npeThrown = false;
        try {
            props.setProperty("key", null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null value");
        }
    }
}

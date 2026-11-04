/*
 * @test
 * @summary Test java.security.Security.setProperty(String, String) for NPE and normal behavior
 * @run main SecurityWitupTest
 */

import java.security.Security;
import java.util.Properties;

public class SecurityWitupTest {
    public static void main(String[] args) {
        testSetPropertyNormal();
        testSetPropertyNullDatum();
    }

    private static void testSetPropertyNormal() {
        String key = "test.key";
        String value = "test.value";

        // Set property normally
        Security.setProperty(key, value);

        // Verify property was set
        Properties props = getSecurityProperties();
        if (!value.equals(props.getProperty(key))) {
            throw new RuntimeException("Property value mismatch: expected '" + value + "' but got '" + props.getProperty(key) + "'");
        }
    }

    private static void testSetPropertyNullDatum() {
        String key = "test.null.value";
        String value = null;

        try {
            Security.setProperty(key, value);
            throw new RuntimeException("Expected NullPointerException when setting null datum, but none thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    // Helper method to access the private static props field via reflection
    // Reflection is used because props is private and no public getter exists
    private static Properties getSecurityProperties() {
        try {
            java.lang.reflect.Field propsField = Security.class.getDeclaredField("props");
            propsField.setAccessible(true);
            Object propsObj = propsField.get(null);
            if (propsObj instanceof Properties) {
                return (Properties) propsObj;
            } else {
                throw new RuntimeException("Security.props is not a Properties instance");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to access Security.props field", e);
        }
    }
}

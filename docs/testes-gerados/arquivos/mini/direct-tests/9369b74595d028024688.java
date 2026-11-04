/*
 * @test
 * @summary Test java.security.Security.setProperty behavior
 * @run main SecurityWitupTest
 */

import java.security.Security;
import java.util.Properties;

public class SecurityWitupTest {
    public static void main(String[] args) throws Exception {
        // Save original property value to restore later
        String testKey = "test.key.for.setProperty";
        String originalValue = System.getProperty(testKey);

        // Set a property using Security.setProperty
        String newValue = "newValue";
        Security.setProperty(testKey, newValue);

        // Check that the property was set in Security.props
        // Since props is private, verify via Security.getProperty
        String propValue = Security.getProperty(testKey);
        if (!newValue.equals(propValue)) {
            throw new RuntimeException("Security.setProperty did not set the property correctly");
        }

        // Overwrite the property with a different value
        String anotherValue = "anotherValue";
        Security.setProperty(testKey, anotherValue);
        String propValue2 = Security.getProperty(testKey);
        if (!anotherValue.equals(propValue2)) {
            throw new RuntimeException("Security.setProperty did not overwrite the property correctly");
        }

        // Test with empty string value
        Security.setProperty(testKey, "");
        String emptyValue = Security.getProperty(testKey);
        if (!"".equals(emptyValue)) {
            throw new RuntimeException("Security.setProperty did not set empty string value correctly");
        }

        // Test with null value - should store null (removes the property)
        Security.setProperty(testKey, null);
        String nullValue = Security.getProperty(testKey);
        if (nullValue != null) {
            throw new RuntimeException("Security.setProperty did not remove the property when setting null");
        }

        System.out.println("All tests passed.");
    }
}

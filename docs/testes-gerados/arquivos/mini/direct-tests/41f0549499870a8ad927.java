/*
 * @test
 * @summary Test java.lang.System.getProperty(String key, String def) behavior
 * @run main SystemWitupTest
 */
public class SystemWitupTest {
    public static void main(String[] args) {
        // Test with a known property key that should exist
        String javaVersion = System.getProperty("java.version", "defaultVersion");
        if (javaVersion == null || javaVersion.isEmpty()) {
            throw new RuntimeException("Expected non-null, non-empty java.version property");
        }

        // Test with a non-existent property key, expect default value returned
        String defaultValue = "defaultValue";
        String result = System.getProperty("non.existent.property.key", defaultValue);
        if (!defaultValue.equals(result)) {
            throw new RuntimeException("Expected default value for non-existent property key");
        }

        // Test with empty string key - should throw IllegalArgumentException as per checkKey contract
        try {
            System.getProperty("", "def");
            throw new RuntimeException("Expected IllegalArgumentException for empty key");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test with null key - should throw NullPointerException as per checkKey contract
        try {
            System.getProperty(null, "def");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}

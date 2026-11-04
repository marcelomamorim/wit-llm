/*
 * @test
 * @summary Test System.getProperty(String key, String def) for key validation and default return
 * @run main SystemWitupTest
 */

public class SystemWitupTest {
    public static void main(String[] args) {
        testNullKeyThrowsNPE();
        testEmptyKeyThrowsIAE();
        testValidKeyReturnsValueOrDefault();
    }

    private static void testNullKeyThrowsNPE() {
        try {
            System.getProperty(null, "default");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testEmptyKeyThrowsIAE() {
        try {
            System.getProperty("", "default");
            throw new RuntimeException("Expected IllegalArgumentException for empty key");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testValidKeyReturnsValueOrDefault() {
        String key = "java.lang.SystemWitupTest.key";
        String def = "defaultValue";

        // Clear property if set
        System.clearProperty(key);

        // When property not set, should return default
        String result = System.getProperty(key, def);
        if (!def.equals(result)) {
            throw new RuntimeException("Expected default value when property not set");
        }

        // Set property and verify getProperty returns it
        System.setProperty(key, "setValue");
        result = System.getProperty(key, def);
        if (!"setValue".equals(result)) {
            throw new RuntimeException("Expected set property value to be returned");
        }

        // Clean up
        System.clearProperty(key);
    }
}

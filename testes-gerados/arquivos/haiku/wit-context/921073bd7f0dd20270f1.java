/*
 * @test
 * @summary Verify System.getProperty(String) rejects null and empty keys and returns null for absent keys
 * @run main SystemWitupTest
 */

public class SystemWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsNullPointerException();
        testEmptyKeyThrowsIllegalArgumentException();
        testMissingKeyReturnsNull();
        testExistingKeyReturnsNonNull();
    }

    private static void testNullKeyThrowsNullPointerException() {
        try {
            System.getProperty(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("Expected NullPointerException for null key");
    }

    private static void testEmptyKeyThrowsIllegalArgumentException() {
        try {
            System.getProperty("");
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for empty key");
    }

    private static void testMissingKeyReturnsNull() {
        String value = System.getProperty("witup.missing.property.7f3a");
        if (value != null) {
            throw new AssertionError("Expected null for missing key but got: " + value);
        }
    }

    private static void testExistingKeyReturnsNonNull() {
        String value = System.getProperty("java.version");
        if (value == null) {
            throw new AssertionError("Expected non-null value for java.version");
        }
    }
}

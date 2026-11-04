/*
 * @test
 * @summary Test requireServiceProviderName method of jdk.internal.module.Checks
 * @run main ChecksWitupTest
 */

public class ChecksWitupTest {
    public static void main(String[] args) {
        testValidName();
        testInvalidName();
        testNullName();
    }

    private static void testValidName() {
        // Valid qualified class name
        String valid = "java.lang.String";
        String result = jdk.internal.module.Checks.requireServiceProviderName(valid);
        if (!valid.equals(result)) {
            throw new RuntimeException("Expected '" + valid + "' but got '" + result + "'");
        }
    }

    private static void testInvalidName() {
        // Invalid qualified class name: contains invalid identifier
        String invalid = "java.lang.123Invalid";
        try {
            jdk.internal.module.Checks.requireServiceProviderName(invalid);
            throw new RuntimeException("Expected IllegalArgumentException for invalid name");
        } catch (IllegalArgumentException e) {
            // expected
            String msg = e.getMessage();
            if (!msg.contains(invalid) || !msg.contains("service provider name")) {
                throw new RuntimeException("Exception message does not contain expected text: " + msg);
            }
        }
    }

    private static void testNullName() {
        try {
            jdk.internal.module.Checks.requireServiceProviderName(null);
            throw new RuntimeException("Expected IllegalArgumentException for null name");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (!"Null service provider name".equals(msg)) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}

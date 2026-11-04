/*
 * @test
 * @summary Test requireServiceTypeName method of jdk.internal.module.Checks
 * @run main ChecksWitupTest
 */

public class ChecksWitupTest {
    public static void main(String[] args) {
        testValidServiceTypeName();
        testInvalidServiceTypeName();
        testNullServiceTypeName();
    }

    private static void testValidServiceTypeName() {
        // Valid qualified class name
        String validName = "java.util.List";
        String result = jdk.internal.module.Checks.requireServiceTypeName(validName);
        if (!validName.equals(result)) {
            throw new AssertionError("Expected '" + validName + "' but got '" + result + "'");
        }
    }

    private static void testInvalidServiceTypeName() {
        // Invalid qualified class name: contains invalid identifier
        String invalidName = "java.util.123List";
        try {
            jdk.internal.module.Checks.requireServiceTypeName(invalidName);
            throw new AssertionError("Expected IllegalArgumentException for invalid service type name");
        } catch (IllegalArgumentException e) {
            // Expected
            if (!e.getMessage().contains("service type name")) {
                throw new AssertionError("Exception message does not mention 'service type name': " + e.getMessage());
            }
        }
    }

    private static void testNullServiceTypeName() {
        try {
            jdk.internal.module.Checks.requireServiceTypeName(null);
            throw new AssertionError("Expected IllegalArgumentException for null service type name");
        } catch (IllegalArgumentException e) {
            // Expected
            if (!e.getMessage().contains("service type name")) {
                throw new AssertionError("Exception message does not mention 'service type name': " + e.getMessage());
            }
        }
    }
}

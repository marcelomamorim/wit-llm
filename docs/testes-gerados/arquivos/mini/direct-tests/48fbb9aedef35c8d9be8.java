/*
 * @test
 * @summary Test requirePackageName method of jdk.internal.module.Checks
 * @run main ChecksWitupTest
 */

public class ChecksWitupTest {
    public static void main(String[] args) {
        testValidPackageName();
        testInvalidPackageName();
        testNullPackageName();
    }

    private static void testValidPackageName() {
        // Valid package names
        String[] validNames = {
            "java",
            "java.base",
            "jdk.internal.module",
            "com.example.project"
        };
        for (String name : validNames) {
            String result = jdk.internal.module.Checks.requirePackageName(name);
            if (!result.equals(name)) {
                throw new AssertionError("Expected '" + name + "' but got '" + result + "'");
            }
        }
    }

    private static void testInvalidPackageName() {
        // Invalid package names that should throw IllegalArgumentException
        String[] invalidNames = {
            "java..base",    // empty identifier
            "java.base.",    // ends with dot
            ".java.base",    // starts with dot
            "java.base.123", // identifier starting with digit
            "java.base-foo", // invalid character '-'
            "java base"      // space in identifier
        };
        for (String name : invalidNames) {
            try {
                jdk.internal.module.Checks.requirePackageName(name);
                throw new AssertionError("Expected IllegalArgumentException for: " + name);
            } catch (IllegalArgumentException e) {
                // expected
            }
        }
    }

    private static void testNullPackageName() {
        try {
            jdk.internal.module.Checks.requirePackageName(null);
            throw new AssertionError("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

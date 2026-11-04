/*
 * @test
 * @summary Test requirePackageName method in jdk.internal.module.Checks
 * @run main ChecksWitupTest
 */

public class ChecksWitupTest {
    public static void main(String[] args) {
        testRequirePackageNameNull();
        testRequirePackageNameValid();
        testRequirePackageNameInvalid();
    }

    private static void testRequirePackageNameNull() {
        try {
            jdk.internal.module.Checks.requirePackageName(null);
            throw new Error("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            // expected
            if (!"Null package name".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testRequirePackageNameValid() {
        // Valid package names
        String[] validNames = {
            "java",
            "java.base",
            "jdk.internal.module",
            "com.example.test"
        };
        for (String name : validNames) {
            String result = jdk.internal.module.Checks.requirePackageName(name);
            if (!name.equals(result)) {
                throw new Error("requirePackageName returned unexpected result for valid name: " + name);
            }
        }
    }

    private static void testRequirePackageNameInvalid() {
        // Invalid package names
        String[] invalidNames = {
            "",          // empty string
            "java..base", // empty identifier between dots
            "java.base.", // ends with dot
            ".java.base", // starts with dot
            "java.base.123", // identifier starting with digit
            "java.base.-mod", // invalid character
            "java.base.@mod" // invalid character
        };
        for (String name : invalidNames) {
            try {
                jdk.internal.module.Checks.requirePackageName(name);
                throw new Error("Expected IllegalArgumentException for invalid name: " + name);
            } catch (IllegalArgumentException e) {
                // expected
                if (!e.getMessage().contains("Invalid") && !e.getMessage().contains("Null")) {
                    throw new Error("Unexpected exception message for invalid name '" + name + "': " + e.getMessage());
                }
            }
        }
    }
}

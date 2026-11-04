/*
 * @test
 * @summary Test requireModuleName method of jdk.internal.module.Checks
 * @run main ChecksWitupTest
 */

public class ChecksWitupTest {
    public static void main(String[] args) {
        testValidModuleNames();
        testInvalidModuleNames();
        testNullModuleName();
    }

    private static void testValidModuleNames() {
        // Simple valid module name
        String name1 = "java.base";
        String result1 = jdk.internal.module.Checks.requireModuleName(name1);
        if (!result1.equals(name1)) {
            throw new RuntimeException("Expected '" + name1 + "' but got '" + result1 + "'");
        }

        // Single identifier module name
        String name2 = "module";
        String result2 = jdk.internal.module.Checks.requireModuleName(name2);
        if (!result2.equals(name2)) {
            throw new RuntimeException("Expected '" + name2 + "' but got '" + result2 + "'");
        }

        // Multiple segments
        String name3 = "com.example.module";
        String result3 = jdk.internal.module.Checks.requireModuleName(name3);
        if (!result3.equals(name3)) {
            throw new RuntimeException("Expected '" + name3 + "' but got '" + result3 + "'");
        }
    }

    private static void testInvalidModuleNames() {
        // Identifier with invalid character
        try {
            jdk.internal.module.Checks.requireModuleName("java.ba$se");
            throw new RuntimeException("Expected IllegalArgumentException for invalid character");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("Invalid module name")) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }

        // Identifier starting with digit
        try {
            jdk.internal.module.Checks.requireModuleName("9java.base");
            throw new RuntimeException("Expected IllegalArgumentException for identifier starting with digit");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("Invalid module name")) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }

        // Empty segment
        try {
            jdk.internal.module.Checks.requireModuleName("java..base");
            throw new RuntimeException("Expected IllegalArgumentException for empty segment");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("Invalid module name")) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }

        // Trailing dot
        try {
            jdk.internal.module.Checks.requireModuleName("java.base.");
            throw new RuntimeException("Expected IllegalArgumentException for trailing dot");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("Invalid module name")) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testNullModuleName() {
        try {
            jdk.internal.module.Checks.requireModuleName(null);
            throw new RuntimeException("Expected IllegalArgumentException for null module name");
        } catch (IllegalArgumentException e) {
            if (!"Null module name".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}

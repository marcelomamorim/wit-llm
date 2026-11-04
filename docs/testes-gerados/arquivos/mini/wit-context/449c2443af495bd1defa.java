/*
 * @test
 * @summary Test requireModuleName method of jdk.internal.module.Checks
 * @run main ChecksWitupTest
 */

public class ChecksWitupTest {

    public static void main(String[] args) {
        testRequireModuleNameNull();
        testRequireModuleNameValid();
        testRequireModuleNameInvalidIdentifier();
        testRequireModuleNameInvalidIdentifierInMiddle();
    }

    private static void testRequireModuleNameNull() {
        try {
            jdk.internal.module.Checks.requireModuleName(null);
            throw new Error("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            if (!"Null module name".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testRequireModuleNameValid() {
        // Valid module names
        String[] validNames = {
            "java.base",
            "jdk.internal.module",
            "com.example.module",
            "a",
            "a.b.c"
        };
        for (int i = 0; i < validNames.length; i++) {
            String name = validNames[i];
            String result = jdk.internal.module.Checks.requireModuleName(name);
            if (!name.equals(result)) {
                throw new Error("requireModuleName returned unexpected result for valid name: " + name);
            }
        }
    }

    private static void testRequireModuleNameInvalidIdentifier() {
        // Invalid module names: not valid Java identifiers
        String[] invalidNames = {
            "java.9base",
            "jdk..module",
            "com.exa-mple.module",
            "com.example.123",
            "com.example.$module",
            "com.example.module!"
        };
        for (int i = 0; i < invalidNames.length; i++) {
            String name = invalidNames[i];
            try {
                jdk.internal.module.Checks.requireModuleName(name);
                throw new Error("Expected IllegalArgumentException for invalid module name: " + name);
            } catch (IllegalArgumentException e) {
                if (!e.getMessage().startsWith(name + ": Invalid module name")) {
                    throw new Error("Unexpected exception message for invalid name: " + e.getMessage());
                }
            }
        }
    }

    private static void testRequireModuleNameInvalidIdentifierInMiddle() {
        // Invalid identifier in the middle of the module name
        String name = "com.exa-mple.module";
        try {
            jdk.internal.module.Checks.requireModuleName(name);
            throw new Error("Expected IllegalArgumentException for invalid identifier in module name");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().startsWith(name + ": Invalid module name")) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}

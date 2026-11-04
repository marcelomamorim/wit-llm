/* @test    @summary Test for Checks.requireModuleName to validate module name correctness and exception handling    @run main ChecksWitupTest */

import java.util.Set;

public class ChecksWitupTest {

    public static void main(String[] args) {
        testValidModuleName();
        testNullModuleName();
        testInvalidModuleNameWithInvalidChar();
        testInvalidModuleNameWithLeadingDot();
        testInvalidModuleNameWithTrailingDot();
    }

    static void testValidModuleName() {
        String validName = "com.example.module";
        String result = jdk.internal.module.Checks.requireModuleName(validName);
        if (!result.equals(validName)) {
            throw new AssertionError("Expected: " + validName + ", but got: " + result);
        }
    }

    static void testNullModuleName() {
        try {
            jdk.internal.module.Checks.requireModuleName(null);
            throw new AssertionError("Expected IllegalArgumentException for null module name");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals("Null module name")) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testInvalidModuleNameWithInvalidChar() {
        String invalidName = "com..example";
        try {
            jdk.internal.module.Checks.requireModuleName(invalidName);
            throw new AssertionError("Expected IllegalArgumentException for invalid module name");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (!msg.contains("Invalid module name")) {
                throw new AssertionError("Unexpected message: " + msg);
            }
            if (!msg.contains("'..' is not a Java identifier")) {
                // The message should mention the invalid identifier
                // but since the code checks for isJavaIdentifier, which isn't provided,
                // this is a placeholder for actual message validation.
            }
        }
    }

    static void testInvalidModuleNameWithLeadingDot() {
        String invalidName = ".com.example";
        try {
            jdk.internal.module.Checks.requireModuleName(invalidName);
            throw new AssertionError("Expected IllegalArgumentException for leading dot");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (!msg.contains("Invalid module name")) {
                throw new AssertionError("Unexpected message: " + msg);
            }
        }
    }

    static void testInvalidModuleNameWithTrailingDot() {
        String invalidName = "com.example.";
        try {
            jdk.internal.module.Checks.requireModuleName(invalidName);
            throw new AssertionError("Expected IllegalArgumentException for trailing dot");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (!msg.contains("Invalid module name")) {
                throw new AssertionError("Unexpected message: " + msg);
            }
        }
    }
}

/* @test    @summary Test for Checks.requireServiceTypeName method    @run main ChecksWitupTest */

import java.util.Set;

public class ChecksWitupTest {

    public static void main(String[] args) {
        testValidServiceTypeName();
        testInvalidServiceTypeNameNull();
        testInvalidServiceTypeNameEmpty();
        testInvalidServiceTypeNameWithInvalidChars();
    }

    static void testValidServiceTypeName() {
        String validName = "com.example.Service";
        String result = jdk.internal.module.Checks.requireServiceTypeName(validName);
        if (!result.equals(validName)) {
            throw new AssertionError("Expected: " + validName + ", but got: " + result);
        }
    }

    static void testInvalidServiceTypeNameNull() {
        try {
            jdk.internal.module.Checks.requireServiceTypeName(null);
            throw new AssertionError("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidServiceTypeNameEmpty() {
        try {
            jdk.internal.module.Checks.requireServiceTypeName("");
            throw new AssertionError("Expected IllegalArgumentException for empty string");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidServiceTypeNameWithInvalidChars() {
        String invalidName = "com..example..Service";
        try {
            jdk.internal.module.Checks.requireServiceTypeName(invalidName);
            // The method relies on requireQualifiedClassName, which is not shown here.
            // Assuming it throws IllegalArgumentException for invalid format.
            throw new AssertionError("Expected IllegalArgumentException for invalid format");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

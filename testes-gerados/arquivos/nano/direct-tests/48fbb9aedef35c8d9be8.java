/* @test    @summary Test for Checks.requirePackageName method to ensure it accepts valid package names and throws for invalid ones.    @run main ChecksWitupTest */

import java.util.Set;

public class ChecksWitupTest {

    public static void main(String[] args) {
        testValidPackageName();
        testInvalidPackageName();
    }

    static void testValidPackageName() {
        String validName = "com.example.package";
        String result = jdk.internal.module.Checks.requirePackageName(validName);
        if (!result.equals(validName)) {
            throw new AssertionError("Expected: " + validName + ", but got: " + result);
        }
    }

    static void testInvalidPackageName() {
        String invalidName = "com..example";
        try {
            jdk.internal.module.Checks.requirePackageName(invalidName);
            throw new AssertionError("Expected IllegalArgumentException for invalid package name")
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}

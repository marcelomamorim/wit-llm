/*
 * @test
 * @summary Test requireServiceTypeName method in jdk.internal.module.Checks
 * @run main ChecksWitupTest
 */

import jdk.internal.module.Checks;

public class ChecksWitupTest {
    public static void main(String[] args) {
        testRequireServiceTypeNameNull();
        testRequireServiceTypeNameValid();
        testRequireServiceTypeNameInvalid();
    }

    private static void testRequireServiceTypeNameNull() {
        try {
            Checks.requireServiceTypeName(null);
            throw new Error("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testRequireServiceTypeNameValid() {
        // valid qualified class name
        String validName = "java.lang.String";
        String result = Checks.requireServiceTypeName(validName);
        if (!validName.equals(result)) {
            throw new Error("Expected returned name to be same as input");
        }
    }

    private static void testRequireServiceTypeNameInvalid() {
        // invalid qualified class name: contains invalid identifier
        String invalidName = "java.9lang.String";
        try {
            Checks.requireServiceTypeName(invalidName);
            throw new Error("Expected IllegalArgumentException for invalid qualified class name");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

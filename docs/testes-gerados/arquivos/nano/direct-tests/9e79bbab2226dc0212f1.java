/* @test    @summary Test for Checks.requireServiceProviderName method    @run main ChecksWitupTest */

import jdk.internal.module.Checks;

public class ChecksWitupTest {

    public static void main(String[] args) {
        testValidServiceProviderName();
        testNullServiceProviderName();
        testInvalidServiceProviderName();
    }

    static void testValidServiceProviderName() {
        String validName = "com.example.ServiceProvider";
        String result = Checks.requireServiceProviderName(validName);
        if (!result.equals(validName)) {
            throw new AssertionError("Expected: " + validName + ", but got: " + result);
        }
    }

    static void testNullServiceProviderName() {
        try {
            Checks.requireServiceProviderName(null);
            throw new AssertionError("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidServiceProviderName() {
        String invalidName = "com..example..Service";
        try {
            Checks.requireServiceProviderName(invalidName);
            throw new AssertionError("Expected IllegalArgumentException for invalid name");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

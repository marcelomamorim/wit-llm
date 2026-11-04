/* @test    @summary Test for PasswordCallback constructor with null or empty prompt to verify IllegalArgumentException is thrown.    @run main PasswordCallbackWitupTest */

import javax.security.auth.callback.PasswordCallback;

public class PasswordCallbackWitupTest {

    public static void main(String[] args) {
        testNullPrompt();
        testEmptyPrompt();
    }

    private static void testNullPrompt() {
        try {
            new PasswordCallback(null, true);
            System.err.println("Failed: Expected IllegalArgumentException for null prompt");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown for null prompt");
        }
    }

    private static void testEmptyPrompt() {
        try {
            new PasswordCallback("", false);
            System.err.println("Failed: Expected IllegalArgumentException for empty prompt");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown for empty prompt");
        }
    }
}

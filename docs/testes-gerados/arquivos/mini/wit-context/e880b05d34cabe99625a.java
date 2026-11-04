/*
 * @test
 * @summary Test NameCallback(String prompt) constructor for valid and invalid inputs
 * @run main NameCallbackWitupTest
 */

import javax.security.auth.callback.NameCallback;

public class NameCallbackWitupTest {
    public static void main(String[] args) {
        testValidPrompt();
        testNullPrompt();
        testEmptyPrompt();
    }

    private static void testValidPrompt() {
        String prompt = "Enter your name:";
        NameCallback nc = new NameCallback(prompt);
        if (!prompt.equals(nc.getPrompt())) {
            throw new AssertionError("Prompt mismatch: expected '" + prompt + "' but got '" + nc.getPrompt() + "'");
        }
    }

    private static void testNullPrompt() {
        try {
            new NameCallback(null);
            throw new AssertionError("Expected IllegalArgumentException for null prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testEmptyPrompt() {
        try {
            new NameCallback("");
            throw new AssertionError("Expected IllegalArgumentException for empty prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}

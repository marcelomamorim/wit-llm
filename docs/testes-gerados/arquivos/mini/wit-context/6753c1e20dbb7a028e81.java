/*
 * @test
 * @summary Test TextInputCallback(String prompt) constructor for valid and invalid inputs
 * @run main TextInputCallbackWitupTest
 */

import javax.security.auth.callback.TextInputCallback;

public class TextInputCallbackWitupTest {
    public static void main(String[] args) {
        testValidPrompt();
        testNullPrompt();
        testEmptyPrompt();
    }

    private static void testValidPrompt() {
        String prompt = "Enter your name:";
        TextInputCallback tic = new TextInputCallback(prompt);
        if (!prompt.equals(tic.getPrompt())) {
            throw new RuntimeException("Prompt mismatch: expected '" + prompt + "' but got '" + tic.getPrompt() + "'");
        }
    }

    private static void testNullPrompt() {
        try {
            new TextInputCallback(null);
            throw new RuntimeException("Expected IllegalArgumentException for null prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testEmptyPrompt() {
        try {
            new TextInputCallback("");
            throw new RuntimeException("Expected IllegalArgumentException for empty prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}

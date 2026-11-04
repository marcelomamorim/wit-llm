/*
 * @test
 * @summary Test NameCallback(String prompt) constructor behavior
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
        String prompt = "Enter username:";
        NameCallback nc = new NameCallback(prompt);
        if (!prompt.equals(nc.getPrompt())) {
            throw new RuntimeException("Prompt mismatch: expected '" + prompt + "' but got '" + nc.getPrompt() + "'");
        }
    }

    private static void testNullPrompt() {
        try {
            new NameCallback(null);
            throw new RuntimeException("Expected IllegalArgumentException for null prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testEmptyPrompt() {
        try {
            new NameCallback("");
            throw new RuntimeException("Expected IllegalArgumentException for empty prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Test PasswordCallback constructor behavior with valid and invalid prompts
 * @run main PasswordCallbackWitupTest
 */

import javax.security.auth.callback.PasswordCallback;

public class PasswordCallbackWitupTest {
    public static void main(String[] args) {
        testValidPrompt();
        testNullPrompt();
        testEmptyPrompt();
    }

    private static void testValidPrompt() {
        String prompt = "Enter password:";
        boolean echoOn = true;
        PasswordCallback pc = new PasswordCallback(prompt, echoOn);
        if (!prompt.equals(pc.getPrompt())) {
            throw new RuntimeException("Prompt mismatch: expected '" + prompt + "' but got '" + pc.getPrompt() + "'");
        }
        if (pc.isEchoOn() != echoOn) {
            throw new RuntimeException("EchoOn mismatch: expected " + echoOn + " but got " + pc.isEchoOn());
        }
    }

    private static void testNullPrompt() {
        try {
            new PasswordCallback(null, true);
            throw new RuntimeException("Expected IllegalArgumentException for null prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testEmptyPrompt() {
        try {
            new PasswordCallback("", false);
            throw new RuntimeException("Expected IllegalArgumentException for empty prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Test javax.security.auth.callback.PasswordCallback constructor behavior
 * @run main PasswordCallbackWitupTest
 */

import javax.security.auth.callback.PasswordCallback;

public class PasswordCallbackWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testConstructorNullPrompt();
        testConstructorEmptyPrompt();
    }

    private static void testValidConstructor() {
        String prompt = "Enter password:";
        boolean echoOn = true;
        PasswordCallback pc = new PasswordCallback(prompt, echoOn);
        if (!prompt.equals(pc.getPrompt())) {
            throw new RuntimeException("Prompt mismatch");
        }
        if (pc.isEchoOn() != echoOn) {
            throw new RuntimeException("EchoOn mismatch");
        }
    }

    private static void testConstructorNullPrompt() {
        try {
            new PasswordCallback(null, true);
            throw new RuntimeException("Expected IllegalArgumentException for null prompt");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testConstructorEmptyPrompt() {
        try {
            new PasswordCallback("", false);
            throw new RuntimeException("Expected IllegalArgumentException for empty prompt");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}

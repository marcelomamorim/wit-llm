/*
 * @test
 * @summary Tests NameCallback(String) argument validation and prompt retrieval
 * @run main NameCallbackWitupTest
 */

import javax.security.auth.callback.NameCallback;

public class NameCallbackWitupTest {

    public static void main(String[] args) {
        NameCallback cb = new NameCallback("Name: ");
        check("Name: ".equals(cb.getPrompt()), "prompt should be preserved");
        check(cb.getDefaultName() == null, "defaultName should be null for one-arg constructor");

        NameCallback single = new NameCallback("x");
        check("x".equals(single.getPrompt()), "single-char prompt should be accepted");

        expectIllegalArgument(null, "null prompt");
        expectIllegalArgument("", "empty prompt");
    }

    private static void expectIllegalArgument(String prompt, String what) {
        try {
            new NameCallback(prompt);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for " + what);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

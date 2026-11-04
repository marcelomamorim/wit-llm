/*
 * @test
 * @summary Verifies NameCallback(String, String) rejects null or empty prompt and defaultName and keeps valid values observable.
 * @run main NameCallbackWitupTest
 */

import javax.security.auth.callback.NameCallback;

public class NameCallbackWitupTest {

    public static void main(String[] args) {
        expectIaeTwoArgs(null, "guest");
        expectIaeTwoArgs("", "guest");
        expectIaeTwoArgs("Name:", null);
        expectIaeTwoArgs("Name:", "");
        expectIaeTwoArgs(null, null);
        expectIaeTwoArgs("", "");

        NameCallback cb = new NameCallback("Name:", "guest");
        check("Name:".equals(cb.getPrompt()), "prompt mismatch: " + cb.getPrompt());
        check("guest".equals(cb.getDefaultName()), "defaultName mismatch: " + cb.getDefaultName());

        cb.setName("alice");
        check("alice".equals(cb.getName()), "name mismatch after setName: " + cb.getName());

        NameCallback single = new NameCallback("Login:");
        check("Login:".equals(single.getPrompt()), "single-arg prompt mismatch");
        check(single.getDefaultName() == null, "single-arg defaultName should be null");
    }

    private static void expectIaeTwoArgs(String prompt, String defaultName) {
        try {
            new NameCallback(prompt, defaultName);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for prompt=" + prompt
                + ", defaultName=" + defaultName);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

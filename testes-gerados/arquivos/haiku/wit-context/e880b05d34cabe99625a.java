/*
 * @test
 * @summary Verify NameCallback(String) rejects null and empty prompts and keeps valid prompts observable
 * @run main NameCallbackWitupTest
 */

import javax.security.auth.callback.NameCallback;

public class NameCallbackWitupTest {
    public static void main(String[] args) {
        expectIllegalArgument(null);
        expectIllegalArgument("");

        NameCallback cb = new NameCallback("Name:");
        if (!"Name:".equals(cb.getPrompt())) {
            throw new RuntimeException("unexpected prompt: " + cb.getPrompt());
        }
        if (cb.getDefaultName() != null) {
            throw new RuntimeException("default name should be null");
        }
        if (cb.getName() != null) {
            throw new RuntimeException("name should be null before setName");
        }
    }

    static void expectIllegalArgument(String prompt) {
        try {
            new NameCallback(prompt);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for prompt: " + prompt);
    }
}

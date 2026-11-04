/*
 * @test
 * @summary Verifies TextInputCallback(String) prompt validation and retrieval
 * @run main TextInputCallbackWitupTest
 */

import javax.security.auth.callback.TextInputCallback;

public class TextInputCallbackWitupTest {
    public static void main(String[] args) {
        TextInputCallback cb = new TextInputCallback("Name:");
        if (!"Name:".equals(cb.getPrompt())) {
            throw new RuntimeException("prompt mismatch");
        }
        if (cb.getDefaultText() != null) {
            throw new RuntimeException("defaultText should be null");
        }

        TextInputCallback spaced = new TextInputCallback(" ");
        if (!" ".equals(spaced.getPrompt())) {
            throw new RuntimeException("whitespace prompt should be accepted");
        }

        expectIllegalArgument(null);
        expectIllegalArgument("");
    }

    private static void expectIllegalArgument(String prompt) {
        try {
            new TextInputCallback(prompt);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for prompt: " + prompt);
    }
}

/*
 * @test
 * @summary Verifies that the two-argument TextInputCallback constructor rejects null or empty prompt and defaultText and accepts valid input
 * @run main TextInputCallbackWitupTest
 */

import javax.security.auth.callback.TextInputCallback;

public class TextInputCallbackWitupTest {

    public static void main(String[] args) {
        expectIllegalArgument(null, "default");
        expectIllegalArgument("", "default");
        expectIllegalArgument("prompt", null);
        expectIllegalArgument("prompt", "");
        expectIllegalArgument(null, null);
        expectIllegalArgument("", "");

        TextInputCallback cb = new TextInputCallback("Name:", "guest");
        check("Name:".equals(cb.getPrompt()), "prompt mismatch");
        check("guest".equals(cb.getDefaultText()), "defaultText mismatch");
    }

    static void expectIllegalArgument(String prompt, String defaultText) {
        try {
            new TextInputCallback(prompt, defaultText);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for prompt="
                + prompt + ", defaultText=" + defaultText);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

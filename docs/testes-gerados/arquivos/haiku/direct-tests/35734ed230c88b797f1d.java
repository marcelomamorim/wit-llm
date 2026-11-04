/*
 * @test
 * @summary Tests argument validation and accessors of the TextInputCallback two-argument constructor
 * @run main TextInputCallbackWitupTest
 */

import javax.security.auth.callback.TextInputCallback;

public class TextInputCallbackWitupTest {

    public static void main(String[] args) {
        testValidArgumentsAreStored();
        testWhitespaceDefaultTextIsAccepted();
        testNullPromptThrows();
        testEmptyPromptThrows();
        testNullDefaultTextThrows();
        testEmptyDefaultTextThrows();
    }

    static void testValidArgumentsAreStored() {
        TextInputCallback cb = new TextInputCallback("Name:", "guest");
        if (!"Name:".equals(cb.getPrompt())) {
            throw new RuntimeException("Unexpected prompt: " + cb.getPrompt());
        }
        if (!"guest".equals(cb.getDefaultText())) {
            throw new RuntimeException("Unexpected defaultText: " + cb.getDefaultText());
        }
    }

    static void testWhitespaceDefaultTextIsAccepted() {
        TextInputCallback cb = new TextInputCallback("Code:", "  ");
        if (!"  ".equals(cb.getDefaultText())) {
            throw new RuntimeException("Whitespace defaultText not preserved: ["
                    + cb.getDefaultText() + "]");
        }
    }

    static void testNullPromptThrows() {
        expectIllegalArgument(null, "guest");
    }

    static void testEmptyPromptThrows() {
        expectIllegalArgument("", "guest");
    }

    static void testNullDefaultTextThrows() {
        expectIllegalArgument("Name:", null);
    }

    static void testEmptyDefaultTextThrows() {
        expectIllegalArgument("Name:", "");
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
}

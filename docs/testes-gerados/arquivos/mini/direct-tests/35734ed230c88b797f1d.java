/*
 * @test
 * @summary Test TextInputCallback(String prompt, String defaultText) constructor behavior
 * @run main TextInputCallbackWitupTest
 */

import javax.security.auth.callback.TextInputCallback;

public class TextInputCallbackWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testNullPrompt();
        testEmptyPrompt();
        testNullDefaultText();
        testEmptyDefaultText();
    }

    private static void testValidConstructor() {
        String prompt = "Enter name:";
        String defaultText = "default";
        TextInputCallback tic = new TextInputCallback(prompt, defaultText);
        if (!prompt.equals(tic.getPrompt())) {
            throw new RuntimeException("Prompt mismatch");
        }
        if (!defaultText.equals(tic.getDefaultText())) {
            throw new RuntimeException("DefaultText mismatch");
        }
    }

    private static void testNullPrompt() {
        try {
            new TextInputCallback(null, "default");
            throw new RuntimeException("Expected IllegalArgumentException for null prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testEmptyPrompt() {
        try {
            new TextInputCallback("", "default");
            throw new RuntimeException("Expected IllegalArgumentException for empty prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testNullDefaultText() {
        try {
            new TextInputCallback("prompt", null);
            throw new RuntimeException("Expected IllegalArgumentException for null defaultText");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testEmptyDefaultText() {
        try {
            new TextInputCallback("prompt", "");
            throw new RuntimeException("Expected IllegalArgumentException for empty defaultText");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Test TextInputCallback(String prompt, String defaultText) constructor for valid and invalid inputs
 * @run main TextInputCallbackWitupTest
 */

import javax.security.auth.callback.TextInputCallback;

public class TextInputCallbackWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testConstructorNullPrompt();
        testConstructorEmptyPrompt();
        testConstructorNullDefaultText();
        testConstructorEmptyDefaultText();
    }

    private static void testValidConstructor() {
        String prompt = "Enter your name:";
        String defaultText = "default";
        TextInputCallback cb = new TextInputCallback(prompt, defaultText);
        if (!prompt.equals(cb.getPrompt())) {
            throw new RuntimeException("Prompt mismatch");
        }
        if (!defaultText.equals(cb.getDefaultText())) {
            throw new RuntimeException("DefaultText mismatch");
        }
    }

    private static void testConstructorNullPrompt() {
        try {
            new TextInputCallback(null, "default");
            throw new RuntimeException("Expected IllegalArgumentException for null prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testConstructorEmptyPrompt() {
        try {
            new TextInputCallback("", "default");
            throw new RuntimeException("Expected IllegalArgumentException for empty prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testConstructorNullDefaultText() {
        try {
            new TextInputCallback("prompt", null);
            throw new RuntimeException("Expected IllegalArgumentException for null defaultText");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testConstructorEmptyDefaultText() {
        try {
            new TextInputCallback("prompt", "");
            throw new RuntimeException("Expected IllegalArgumentException for empty defaultText");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}

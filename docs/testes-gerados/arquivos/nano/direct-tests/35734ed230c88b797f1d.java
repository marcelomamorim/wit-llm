/* @test    @summary Test constructor of TextInputCallback with valid prompt and defaultText    @run main TextInputCallbackWitupTest */

import javax.security.auth.callback.TextInputCallback;

public class TextInputCallbackWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullPrompt();
        testEmptyPrompt();
        testNullDefaultText();
        testEmptyDefaultText();
    }

    static void testValidConstructor() {
        String prompt = "Enter your name";
        String defaultText = "John Doe";
        TextInputCallback callback = new TextInputCallback(prompt, defaultText);
        assert callback.getPrompt().equals(prompt) : "Prompt mismatch";
        assert callback.getDefaultText().equals(defaultText) : "DefaultText mismatch";
    }

    static void testNullPrompt() {
        try {
            new TextInputCallback(null, "Default");
            assert false : "Expected IllegalArgumentException for null prompt";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testEmptyPrompt() {
        try {
            new TextInputCallback("", "Default");
            assert false : "Expected IllegalArgumentException for empty prompt";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNullDefaultText() {
        try {
            new TextInputCallback("Prompt", null);
            assert false : "Expected IllegalArgumentException for null defaultText";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testEmptyDefaultText() {
        try {
            new TextInputCallback("Prompt", "");
            assert false : "Expected IllegalArgumentException for empty defaultText";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

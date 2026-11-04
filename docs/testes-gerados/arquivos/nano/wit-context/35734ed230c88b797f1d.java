/* @test    @summary Test for TextInputCallback constructor with prompt and defaultText.    @run main TextInputCallbackWitupTest */

import javax.security.auth.callback.TextInputCallback;

public class TextInputCallbackWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidInputs();
        testConstructorWithNullPrompt();
        testConstructorWithEmptyPrompt();
        testConstructorWithNullDefaultText();
        testConstructorWithEmptyDefaultText();
    }

    static void testConstructorWithValidInputs() {
        String prompt = "Enter your name";
        String defaultText = "John Doe";
        TextInputCallback callback = new TextInputCallback(prompt, defaultText);
        assert callback.getPrompt().equals(prompt) : "Prompt mismatch";
        assert callback.getDefaultText().equals(defaultText) : "DefaultText mismatch";
        System.out.println("testConstructorWithValidInputs passed");
    }

    static void testConstructorWithNullPrompt() {
        try {
            new TextInputCallback(null, "Default");
            assert false : "Expected IllegalArgumentException for null prompt";
        } catch (IllegalArgumentException e) {
            System.out.println("testConstructorWithNullPrompt passed");
        }
    }

    static void testConstructorWithEmptyPrompt() {
        try {
            new TextInputCallback("", "Default");
            assert false : "Expected IllegalArgumentException for empty prompt";
        } catch (IllegalArgumentException e) {
            System.out.println("testConstructorWithEmptyPrompt passed");
        }
    }

    static void testConstructorWithNullDefaultText() {
        try {
            new TextInputCallback("Prompt", null);
            assert false : "Expected IllegalArgumentException for null defaultText";
        } catch (IllegalArgumentException e) {
            System.out.println("testConstructorWithNullDefaultText passed");
        }
    }

    static void testConstructorWithEmptyDefaultText() {
        try {
            new TextInputCallback("Prompt", "");
            assert false : "Expected IllegalArgumentException for empty defaultText";
        } catch (IllegalArgumentException e) {
            System.out.println("testConstructorWithEmptyDefaultText passed");
        }
    }
}

/* @test    @summary Test for NameCallback constructor with prompt parameter.    @run main NameCallbackWitupTest */

import javax.security.auth.callback.NameCallback;

public class NameCallbackWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidPrompt();
        testConstructorWithNullPrompt();
        testConstructorWithEmptyPrompt();
        testConstructorWithValidPromptAndDefaultName();
        testConstructorWithNullPromptAndDefaultName();
        testConstructorWithEmptyPromptAndDefaultName();
    }

    static void testConstructorWithValidPrompt() {
        String prompt = "Enter your name";
        NameCallback nc = new NameCallback(prompt);
        assert nc.getPrompt().equals(prompt) : "Prompt should match input";
        assert nc.getDefaultName() == null : "Default name should be null";
    }

    static void testConstructorWithNullPrompt() {
        try {
            new NameCallback(null);
            assert false : "Expected IllegalArgumentException for null prompt";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testConstructorWithEmptyPrompt() {
        try {
            new NameCallback("");
            assert false : "Expected IllegalArgumentException for empty prompt";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testConstructorWithValidPromptAndDefaultName() {
        String prompt = "Enter your name";
        String defaultName = "John";
        NameCallback nc = new NameCallback(prompt, defaultName);
        assert nc.getPrompt().equals(prompt) : "Prompt should match input";
        assert nc.getDefaultName().equals(defaultName) : "Default name should match input";
    }

    static void testConstructorWithNullPromptAndDefaultName() {
        try {
            new NameCallback(null, null);
            assert false : "Expected IllegalArgumentException for null prompt and defaultName";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testConstructorWithEmptyPromptAndDefaultName() {
        try {
            new NameCallback("", "");
            assert false : "Expected IllegalArgumentException for empty prompt and defaultName";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

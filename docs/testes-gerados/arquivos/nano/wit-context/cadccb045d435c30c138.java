/* @test    @summary Test for NameCallback constructor with prompt and defaultName, focusing on exception conditions    @run main NameCallbackWitupTest */

import javax.security.auth.callback.NameCallback;

public class NameCallbackWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidInputs();
        testConstructorWithNullPrompt();
        testConstructorWithEmptyPrompt();
        testConstructorWithNullDefaultName();
        testConstructorWithEmptyDefaultName();
    }

    static void testConstructorWithValidInputs() {
        String prompt = "Enter your name";
        String defaultName = "John";
        NameCallback callback = new NameCallback(prompt, defaultName);
        assert callback.getPrompt().equals(prompt) : "Prompt mismatch";
        assert callback.getDefaultName().equals(defaultName) : "DefaultName mismatch";
        assert callback.getName() == null : "Initial name should be null";
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

    static void testConstructorWithNullDefaultName() {
        try {
            new NameCallback("Prompt", null);
            assert false : "Expected IllegalArgumentException for null defaultName";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testConstructorWithEmptyDefaultName() {
        try {
            new NameCallback("Prompt", "");
            assert false : "Expected IllegalArgumentException for empty defaultName";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

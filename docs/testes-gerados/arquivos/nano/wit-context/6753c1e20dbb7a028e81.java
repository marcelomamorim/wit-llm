/* @test    @summary Test for TextInputCallback constructor with prompt parameter.    @run main TextInputCallbackWitupTest */

import javax.security.auth.callback.TextInputCallback;

public class TextInputCallbackWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidPrompt();
        testConstructorWithNullPrompt();
        testConstructorWithEmptyPrompt();
    }

    private static void testConstructorWithValidPrompt() {
        String prompt = "Enter your name:";
        TextInputCallback callback = new TextInputCallback(prompt);
        assert callback.getPrompt().equals(prompt) : "Prompt should match input";
        assert callback.getDefaultText() == null : "DefaultText should be null";
    }

    private static void testConstructorWithNullPrompt() {
        try {
            new TextInputCallback(null);
            assert false : "Expected IllegalArgumentException for null prompt";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithEmptyPrompt() {
        try {
            new TextInputCallback("");
            assert false : "Expected IllegalArgumentException for empty prompt";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}

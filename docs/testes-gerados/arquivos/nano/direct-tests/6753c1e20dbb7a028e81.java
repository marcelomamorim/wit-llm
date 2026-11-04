/* @test    @summary Test constructor of TextInputCallback with valid prompt    @run main TextInputCallbackWitupTest */

import javax.security.auth.callback.TextInputCallback;

public class TextInputCallbackWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidPrompt();
        testConstructorWithNullPrompt();
        testConstructorWithEmptyPrompt();
    }

    static void testConstructorWithValidPrompt() {
        String prompt = "Enter your name:";
        TextInputCallback callback = new TextInputCallback(prompt);
        assert callback.getPrompt().equals(prompt) : "Prompt should match input";
        assert callback.getDefaultText() == null : "Default text should be null";
    }

    static void testConstructorWithNullPrompt() {
        boolean exceptionThrown = false;
        try {
            new TextInputCallback(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for null prompt";
    }

    static void testConstructorWithEmptyPrompt() {
        boolean exceptionThrown = false;
        try {
            new TextInputCallback("");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for empty prompt";
    }
}

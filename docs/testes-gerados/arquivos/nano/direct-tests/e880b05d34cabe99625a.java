/* @test    @summary Test constructor of NameCallback with valid prompt    @run main NameCallbackWitupTest */

import javax.security.auth.callback.NameCallback;

public class NameCallbackWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidPrompt();
        testConstructorWithNullPrompt();
        testConstructorWithEmptyPrompt();
    }

    static void testConstructorWithValidPrompt() {
        String prompt = "Enter your name";
        NameCallback callback = new NameCallback(prompt);
        assert callback.getPrompt().equals(prompt) : "Prompt should match input";
        assert callback.getDefaultName() == null : "Default name should be null";
    }

    static void testConstructorWithNullPrompt() {
        boolean exceptionThrown = false;
        try {
            new NameCallback(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for null prompt";
    }

    static void testConstructorWithEmptyPrompt() {
        boolean exceptionThrown = false;
        try {
            new NameCallback("");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for empty prompt";
    }
}

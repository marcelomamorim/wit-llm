/*
 * @test
 * @summary Test TextInputCallback(String prompt) constructor behavior
 * @run main TextInputCallbackWitupTest
 */

public class TextInputCallbackWitupTest {
    public static void main(String[] args) {
        testValidPrompt();
        testNullPrompt();
        testEmptyPrompt();
    }

    private static void testValidPrompt() {
        String prompt = "Enter your name:";
        javax.security.auth.callback.TextInputCallback cb = new javax.security.auth.callback.TextInputCallback(prompt);
        if (!prompt.equals(cb.getPrompt())) {
            throw new RuntimeException("Prompt not set correctly");
        }
    }

    private static void testNullPrompt() {
        try {
            new javax.security.auth.callback.TextInputCallback(null);
            throw new RuntimeException("Expected IllegalArgumentException for null prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testEmptyPrompt() {
        try {
            new javax.security.auth.callback.TextInputCallback("");
            throw new RuntimeException("Expected IllegalArgumentException for empty prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}

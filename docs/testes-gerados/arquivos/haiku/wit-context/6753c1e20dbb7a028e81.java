/*
 * @test
 * @summary Verify TextInputCallback(String) rejects null and empty prompts and keeps a valid prompt
 * @run main TextInputCallbackWitupTest
 */

import javax.security.auth.callback.TextInputCallback;

public class TextInputCallbackWitupTest {

    public static void main(String[] args) {
        testNullPromptRejected();
        testEmptyPromptRejected();
        testValidPromptAccepted();
        testSingleArgDefaultTextIsNull();
    }

    private static void testNullPromptRejected() {
        try {
            new TextInputCallback(null);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for null prompt");
    }

    private static void testEmptyPromptRejected() {
        try {
            new TextInputCallback("");
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for empty prompt");
    }

    private static void testValidPromptAccepted() {
        String prompt = "Enter name";
        TextInputCallback cb = new TextInputCallback(prompt);
        if (!prompt.equals(cb.getPrompt())) {
            throw new RuntimeException("getPrompt() returned unexpected value: " + cb.getPrompt());
        }
    }

    private static void testSingleArgDefaultTextIsNull() {
        TextInputCallback cb = new TextInputCallback("Enter name");
        if (cb.getDefaultText() != null) {
            throw new RuntimeException("getDefaultText() should be null for single-argument constructor");
        }
    }
}

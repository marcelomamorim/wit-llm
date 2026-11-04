/*
 * @test
 * @summary Test NameCallback(String prompt, String defaultName) constructor behavior
 * @run main NameCallbackWitupTest
 */

public class NameCallbackWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testNullPrompt();
        testEmptyPrompt();
        testNullDefaultName();
        testEmptyDefaultName();
    }

    private static void testValidConstructor() {
        String prompt = "Enter name:";
        String defaultName = "defaultUser";
        javax.security.auth.callback.NameCallback nc = new javax.security.auth.callback.NameCallback(prompt, defaultName);
        if (!prompt.equals(nc.getPrompt())) {
            throw new RuntimeException("Prompt mismatch");
        }
        if (!defaultName.equals(nc.getDefaultName())) {
            throw new RuntimeException("DefaultName mismatch");
        }
    }

    private static void testNullPrompt() {
        try {
            new javax.security.auth.callback.NameCallback(null, "default");
            throw new RuntimeException("Expected IllegalArgumentException for null prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testEmptyPrompt() {
        try {
            new javax.security.auth.callback.NameCallback("", "default");
            throw new RuntimeException("Expected IllegalArgumentException for empty prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testNullDefaultName() {
        try {
            new javax.security.auth.callback.NameCallback("prompt", null);
            throw new RuntimeException("Expected IllegalArgumentException for null defaultName");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testEmptyDefaultName() {
        try {
            new javax.security.auth.callback.NameCallback("prompt", "");
            throw new RuntimeException("Expected IllegalArgumentException for empty defaultName");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}

/*
 * @test
 * @summary Test NameCallback(String prompt, String defaultName) constructor for valid and invalid inputs
 * @run main NameCallbackWitupTest
 */

import javax.security.auth.callback.NameCallback;

public class NameCallbackWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testConstructorNullPrompt();
        testConstructorEmptyPrompt();
        testConstructorNullDefaultName();
        testConstructorEmptyDefaultName();
    }

    private static void testValidConstructor() {
        NameCallback nc = new NameCallback("Enter name", "defaultUser");
        if (!"Enter name".equals(nc.getPrompt())) {
            throw new RuntimeException("Prompt mismatch");
        }
        if (!"defaultUser".equals(nc.getDefaultName())) {
            throw new RuntimeException("Default name mismatch");
        }
    }

    private static void testConstructorNullPrompt() {
        try {
            new NameCallback(null, "default");
            throw new RuntimeException("Expected IllegalArgumentException for null prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testConstructorEmptyPrompt() {
        try {
            new NameCallback("", "default");
            throw new RuntimeException("Expected IllegalArgumentException for empty prompt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testConstructorNullDefaultName() {
        try {
            new NameCallback("prompt", null);
            throw new RuntimeException("Expected IllegalArgumentException for null defaultName");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testConstructorEmptyDefaultName() {
        try {
            new NameCallback("prompt", "");
            throw new RuntimeException("Expected IllegalArgumentException for empty defaultName");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}

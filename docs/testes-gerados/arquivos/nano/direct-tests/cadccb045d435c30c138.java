/* @test    @summary Test constructor of NameCallback with valid prompt and defaultName    @run main NameCallbackWitupTest */

import javax.security.auth.callback.NameCallback;

public class NameCallbackWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullPrompt();
        testEmptyPrompt();
        testNullDefaultName();
        testEmptyDefaultName();
    }

    static void testValidConstructor() {
        String prompt = "Enter your name";
        String defaultName = "John";
        NameCallback nc = new NameCallback(prompt, defaultName);
        assert nc.getPrompt().equals(prompt) : "Prompt mismatch";
        assert nc.getDefaultName().equals(defaultName) : "DefaultName mismatch";
        assert nc.getName() == null : "Initial name should be null";
        nc.setName("Alice");
        assert nc.getName().equals("Alice") : "SetName or getName failed";
    }

    static void testNullPrompt() {
        try {
            new NameCallback(null, "Default");
            assert false : "Expected IllegalArgumentException for null prompt";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testEmptyPrompt() {
        try {
            new NameCallback("", "Default");
            assert false : "Expected IllegalArgumentException for empty prompt";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNullDefaultName() {
        String prompt = "Prompt";
        try {
            new NameCallback(prompt, null);
            assert false : "Expected IllegalArgumentException for null defaultName";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testEmptyDefaultName() {
        String prompt = "Prompt";
        try {
            new NameCallback(prompt, "");
            assert false : "Expected IllegalArgumentException for empty defaultName";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

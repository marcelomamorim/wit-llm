/* @test    @summary Test PasswordCallback constructor with valid inputs    @run main PasswordCallbackWitupTest */

import javax.security.auth.callback.PasswordCallback;

public class PasswordCallbackWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidPromptAndEcho();
        testConstructorWithNullPrompt();
        testConstructorWithEmptyPrompt();
    }

    static void testConstructorWithValidPromptAndEcho() {
        String prompt = "Enter Password";
        boolean echoOn = true;
        PasswordCallback pcb = new PasswordCallback(prompt, echoOn);
        assert pcb.getPrompt().equals(prompt) : "Prompt should match input";
        assert pcb.isEchoOn() == echoOn : "EchoOn should match input";
        char[] password = {'a', 'b', 'c'};
        pcb.setPassword(password);
        char[] retrievedPassword = pcb.getPassword();
        assert java.util.Arrays.equals(retrievedPassword, password) : "Password should match set value";
        // Ensure clone is returned
        password[0] = 'z';
        assert !java.util.Arrays.equals(pcb.getPassword(), password) : "Password clone should be independent";
        pcb.clearPassword();
        assert java.util.Arrays.stream(pcb.getPassword()).allMatch(c -> c == ' ') : "Password should be cleared to spaces";
    }

    static void testConstructorWithNullPrompt() {
        boolean exceptionThrown = false;
        try {
            new PasswordCallback(null, true);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for null prompt";
    }

    static void testConstructorWithEmptyPrompt() {
        boolean exceptionThrown = false;
        try {
            new PasswordCallback("", false);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for empty prompt";
    }
}

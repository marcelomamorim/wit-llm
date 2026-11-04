/*
 * @test
 * @summary Verifies PasswordCallback constructor validation and observable prompt/echo state
 * @run main PasswordCallbackWitupTest
 */

import javax.security.auth.callback.PasswordCallback;

public class PasswordCallbackWitupTest {

    public static void main(String[] args) {
        PasswordCallback pc = new PasswordCallback("Password: ", true);
        check("Password: ".equals(pc.getPrompt()), "prompt should be stored");
        check(pc.isEchoOn(), "echoOn=true should be reported");
        check(pc.getPassword() == null, "password should be null before setPassword");

        PasswordCallback pc2 = new PasswordCallback("x", false);
        check("x".equals(pc2.getPrompt()), "single-character prompt should be accepted");
        check(!pc2.isEchoOn(), "echoOn=false should be reported");

        PasswordCallback pc3 = new PasswordCallback(" ", false);
        check(" ".equals(pc3.getPrompt()), "whitespace-only prompt has length 1 and should be accepted");

        expectIllegalArgument(null, true);
        expectIllegalArgument(null, false);
        expectIllegalArgument("", true);
        expectIllegalArgument("", false);

        System.out.println("PASSED");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void expectIllegalArgument(String prompt, boolean echoOn) {
        try {
            new PasswordCallback(prompt, echoOn);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for prompt=" + prompt);
    }
}

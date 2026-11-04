/*
 * @test
 * @summary Verify PasswordCallback constructor argument validation and observable state
 * @run main PasswordCallbackWitupTest
 */

import javax.security.auth.callback.PasswordCallback;

public class PasswordCallbackWitupTest {

    public static void main(String[] args) {
        checkNullPromptRejected();
        checkEmptyPromptRejected();
        checkValidPromptWithEchoOn();
        checkValidPromptWithEchoOff();
        checkWhitespacePromptAccepted();
    }

    static void checkNullPromptRejected() {
        try {
            new PasswordCallback(null, true);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("expected IllegalArgumentException for null prompt");
    }

    static void checkEmptyPromptRejected() {
        try {
            new PasswordCallback("", false);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("expected IllegalArgumentException for empty prompt");
    }

    static void checkValidPromptWithEchoOn() {
        PasswordCallback pc = new PasswordCallback("Password: ", true);
        if (!"Password: ".equals(pc.getPrompt())) {
            fail("unexpected prompt: " + pc.getPrompt());
        }
        if (!pc.isEchoOn()) {
            fail("expected echoOn to be true");
        }
        if (pc.getPassword() != null) {
            fail("expected no password before setPassword");
        }
    }

    static void checkValidPromptWithEchoOff() {
        PasswordCallback pc = new PasswordCallback("PIN", false);
        if (!"PIN".equals(pc.getPrompt())) {
            fail("unexpected prompt: " + pc.getPrompt());
        }
        if (pc.isEchoOn()) {
            fail("expected echoOn to be false");
        }
    }

    static void checkWhitespacePromptAccepted() {
        PasswordCallback pc = new PasswordCallback(" ", true);
        if (!" ".equals(pc.getPrompt())) {
            fail("whitespace prompt not preserved");
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }
}

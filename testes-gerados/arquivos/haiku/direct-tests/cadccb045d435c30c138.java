/*
 * @test
 * @summary Verify NameCallback two-argument constructor validation and observable accessors
 * @run main NameCallbackWitupTest
 */

import javax.security.auth.callback.NameCallback;

public class NameCallbackWitupTest {

    public static void main(String[] args) {
        expectIAE(() -> new NameCallback(null, null));
        expectIAE(() -> new NameCallback("", null));
        expectIAE(() -> new NameCallback(null, "guest"));
        expectIAE(() -> new NameCallback("", "guest"));
        expectIAE(() -> new NameCallback("Name:", null));
        expectIAE(() -> new NameCallback("Name:", ""));
        expectIAE(() -> new NameCallback("", ""));

        NameCallback cb = new NameCallback("Name:", "guest");
        check("Name:".equals(cb.getPrompt()), "prompt mismatch");
        check("guest".equals(cb.getDefaultName()), "default name mismatch");
        check(cb.getName() == null, "name must be null before setName");

        cb.setName("alice");
        check("alice".equals(cb.getName()), "name mismatch after setName");
        check("guest".equals(cb.getDefaultName()), "default name changed by setName");

        cb.setName(null);
        check(cb.getName() == null, "name must be null after setName(null)");

        NameCallback single = new NameCallback("Login:");
        check("Login:".equals(single.getPrompt()), "single-arg prompt mismatch");
        check(single.getDefaultName() == null, "single-arg default name must be null");

        System.out.println("PASSED");
    }

    private static void expectIAE(Runnable r) {
        try {
            r.run();
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

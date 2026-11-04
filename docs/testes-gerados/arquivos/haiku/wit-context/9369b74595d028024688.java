/*
 * @test
 * @summary Verifies Security.setProperty stores non-null values observably and rejects null datum or null key with NullPointerException
 * @run main SecurityWitupTest
 */

import java.security.Security;

public class SecurityWitupTest {

    public static void main(String[] args) {
        String key = "witup.test.setProperty.key";

        Security.setProperty(key, "first");
        if (!"first".equals(Security.getProperty(key))) {
            throw new RuntimeException("setProperty did not store the value");
        }

        Security.setProperty(key, "second");
        if (!"second".equals(Security.getProperty(key))) {
            throw new RuntimeException("setProperty did not overwrite the value");
        }

        expectNullPointerException(() -> Security.setProperty(key, null));
        if (!"second".equals(Security.getProperty(key))) {
            throw new RuntimeException("value changed after rejected null datum");
        }

        expectNullPointerException(() -> Security.setProperty(null, "value"));
    }

    private static void expectNullPointerException(Runnable action) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException was not thrown");
    }
}

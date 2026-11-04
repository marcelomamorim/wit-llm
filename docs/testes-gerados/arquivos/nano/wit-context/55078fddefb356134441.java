/* @test    @summary Tests for java.security.KeyStore class, focusing on PasswordProtection constructor and exception paths.    @run main KeyStoreWitupTest */

import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.spec.AlgorithmParameterSpec;

public class KeyStoreWitupTest {

    public static void main(String[] args) {
        testPasswordProtectionWithNullPassword();
        testPasswordProtectionWithEmptyPassword();
        testPasswordProtectionWithValidPassword();
    }

    public static void testPasswordProtectionWithNullPassword() {
        try {
            PasswordProtection pp = new PasswordProtection(null);
            // Expect no exception, password field should be null
            if (pp == null) {
                throw new AssertionError("PasswordProtection instance is null");
            }
        } catch (NullPointerException e) {
            throw new AssertionError("Unexpected NullPointerException for null password", e);
        }
    }

    public static void testPasswordProtectionWithEmptyPassword() {
        try {
            char[] empty = new char[0];
            PasswordProtection pp = new PasswordProtection(empty);
            if (pp == null) {
                throw new AssertionError("PasswordProtection instance is null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty password", e);
        }
    }

    public static void testPasswordProtectionWithValidPassword() {
        try {
            char[] pwd = {'a', 'b', 'c'};
            PasswordProtection pp = new PasswordProtection(pwd);
            if (pp == null) {
                throw new AssertionError("PasswordProtection instance is null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid password", e);
        }
    }
}

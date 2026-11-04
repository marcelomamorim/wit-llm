/*
 * @test
 * @summary Test KeyStore.PasswordProtection constructor behavior
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;

public class KeyStoreWitupTest {
    public static void main(String[] args) {
        testPasswordProtectionNullPassword();
        testPasswordProtectionNonNullPassword();
    }

    private static void testPasswordProtectionNullPassword() {
        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(null);
        if (pp.getPassword() != null) {
            throw new RuntimeException("Expected null password when constructed with null");
        }
    }

    private static void testPasswordProtectionNonNullPassword() {
        char[] pwd = new char[] {'a', 'b', 'c'};
        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(pwd);
        char[] storedPwd = pp.getPassword();
        if (storedPwd == null) {
            throw new RuntimeException("PasswordProtection returned null password for non-null input");
        }
        if (storedPwd.length != pwd.length) {
            throw new RuntimeException("Password length mismatch");
        }
        for (int i = 0; i < pwd.length; i++) {
            if (storedPwd[i] != pwd[i]) {
                throw new RuntimeException("Password content mismatch at index " + i);
            }
        }
        // Modify original array to check defensive copy
        pwd[0] = 'z';
        char[] storedPwdAfter = pp.getPassword();
        if (storedPwdAfter[0] == 'z') {
            throw new RuntimeException("PasswordProtection did not clone password defensively");
        }
    }
}

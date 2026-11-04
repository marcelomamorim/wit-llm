/*
 * @test
 * @summary Test java.security.KeyStore.PasswordProtection constructor behavior
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;

public class KeyStoreWitupTest {
    public static void main(String[] args) throws Exception {
        testNullPassword();
        testEmptyPassword();
        testClonePassword();
    }

    private static void testNullPassword() {
        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(null);
        if (pp.getPassword() != null) {
            throw new RuntimeException("Expected null password when constructed with null");
        }
    }

    private static void testEmptyPassword() {
        char[] empty = new char[0];
        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(empty);
        char[] pwd = pp.getPassword();
        if (pwd == null) {
            throw new RuntimeException("Password should not be null when constructed with empty array");
        }
        if (pwd.length != 0) {
            throw new RuntimeException("Password length mismatch for empty array");
        }
    }

    private static void testClonePassword() {
        char[] original = new char[] {'a', 'b', 'c'};
        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(original);
        char[] pwd = pp.getPassword();
        if (pwd == original) {
            throw new RuntimeException("Password array should be cloned, not the same instance");
        }
        if (pwd.length != original.length) {
            throw new RuntimeException("Password length mismatch");
        }
        for (int i = 0; i < pwd.length; i++) {
            if (pwd[i] != original[i]) {
                throw new RuntimeException("Password content mismatch at index " + i);
            }
        }
        // Modify original and check cloned password is unaffected
        original[0] = 'z';
        if (pwd[0] == original[0]) {
            throw new RuntimeException("Password clone should not reflect changes in original array");
        }
    }
}

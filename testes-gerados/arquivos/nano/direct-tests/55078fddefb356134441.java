/* @test    @summary Test KeyStore.PasswordProtection constructor with null and non-null passwords    @run main PasswordProtectionConstructorTest */

import java.security.KeyStore;

public class PasswordProtectionConstructorTest {

    public static void main(String[] args) {
        testNullPassword();
        testNonNullPassword();
    }

    static void testNullPassword() {
        KeyStore.PasswordProtection p = new KeyStore.PasswordProtection(null);
        assert p != null : "PasswordProtection instance should not be null when constructed with null";
        assert p.password == null : "Password should be null when constructed with null";
    }

    static void testNonNullPassword() {
        char[] pwd = {'a', 'b', 'c'};
        KeyStore.PasswordProtection p = new KeyStore.PasswordProtection(pwd);
        assert p != null : "PasswordProtection instance should not be null when constructed with non-null";
        assert p.password != null : "Password array should not be null";
        assert p.password != pwd : "Password array should be cloned, not the same reference";
        assert java.util.Arrays.equals(p.password, pwd) : "Password array contents should match";
        // Modify original array to ensure clone is independent
        pwd[0] = 'z';
        assert !java.util.Arrays.equals(p.password, pwd) : "Cloned password should not reflect changes in original";
    }
}

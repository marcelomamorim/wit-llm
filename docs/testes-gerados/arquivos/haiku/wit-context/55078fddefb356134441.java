/*
 * @test
 * @summary Verify public observable behavior of KeyStore.PasswordProtection(char[]) constructor
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import java.util.Arrays;

public class KeyStoreWitupTest {

    public static void main(String[] args) throws Exception {
        testNullPasswordIsAccepted();
        testPasswordIsCopiedOnConstruction();
        testDefaultAlgorithmAndParametersAreNull();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullPasswordIsAccepted() {
        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(null);
        check(pp.getPassword() == null, "null password should be stored as null");
    }

    static void testPasswordIsCopiedOnConstruction() {
        char[] original = {'a', 'b', 'c'};
        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(original);
        original[0] = 'z';
        char[] stored = pp.getPassword();
        check(stored != null, "password should not be null");
        check(Arrays.equals(stored, new char[] {'a', 'b', 'c'}),
              "constructor must copy the password array");
    }

    static void testDefaultAlgorithmAndParametersAreNull() {
        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(new char[] {'x'});
        check(pp.getProtectionAlgorithm() == null, "protection algorithm should be null");
        check(pp.getProtectionParameters() == null, "protection parameters should be null");
    }
}

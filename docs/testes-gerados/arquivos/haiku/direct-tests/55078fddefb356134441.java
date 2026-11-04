/*
 * @test
 * @summary Verifies that KeyStore.PasswordProtection(char[]) copies the password array and handles null
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import java.util.Arrays;

public class KeyStoreWitupTest {
    public static void main(String[] args) throws Exception {
        char[] src = "secret".toCharArray();
        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(src);
        src[0] = 'X';
        if (!Arrays.equals("secret".toCharArray(), pp.getPassword())) {
            throw new RuntimeException("Password was not defensively copied on construction");
        }
        if (pp.getProtectionAlgorithm() != null) {
            throw new RuntimeException("Protection algorithm should be null");
        }

        KeyStore.PasswordProtection nullPp = new KeyStore.PasswordProtection(null);
        if (nullPp.getPassword() != null) {
            throw new RuntimeException("Null password should be preserved as null");
        }
        if (nullPp.getProtectionAlgorithm() != null) {
            throw new RuntimeException("Protection algorithm should be null for null password");
        }

        char[] emptySrc = new char[0];
        KeyStore.PasswordProtection emptyPp = new KeyStore.PasswordProtection(emptySrc);
        if (emptyPp.getPassword() == null || emptyPp.getPassword().length != 0) {
            throw new RuntimeException("Empty password should yield an empty array");
        }
    }
}

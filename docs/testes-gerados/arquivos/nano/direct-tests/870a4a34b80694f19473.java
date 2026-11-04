/* @test    @summary Test constructor of SecretKeyEntry with valid and null input    @run main KeyStoreWitupTest */

import java.security.KeyStore.SecretKeyEntry;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeyStoreWitupTest {

    public static void main(String[] args) {
        testValidSecretKey();
        testNullSecretKey();
    }

    static void testValidSecretKey() {
        byte[] keyBytes = {1, 2, 3, 4, 5, 6, 7, 8};
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        SecretKeyEntry entry = new SecretKeyEntry(secretKey);
        assert entry != null : "SecretKeyEntry should not be null with valid secretKey";
    }

    static void testNullSecretKey() {
        try {
            new SecretKeyEntry(null);
            assert false : "Expected NullPointerException when secretKey is null";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("invalid null input") : "Exception message mismatch";
        }
    }
}

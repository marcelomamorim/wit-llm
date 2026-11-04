/*
 * @test
 * @summary Test KeyStore.SecretKeyEntry constructor behavior
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Collections;

public class KeyStoreWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidSecretKey();
        testConstructorWithNullSecretKey();
    }

    private static void testConstructorWithValidSecretKey() {
        byte[] keyBytes = new byte[] {1, 2, 3, 4, 5, 6, 7, 8};
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(secretKey);
        if (entry == null) {
            throw new RuntimeException("SecretKeyEntry instance is null");
        }
    }

    private static void testConstructorWithNullSecretKey() {
        try {
            new KeyStore.SecretKeyEntry(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("invalid null input")) {
                throw new RuntimeException("Unexpected NullPointerException message: " + msg);
            }
        }
    }
}

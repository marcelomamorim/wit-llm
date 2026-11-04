/*
 * @test
 * @summary Test KeyStore.SecretKeyEntry constructor for null and valid SecretKey
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Collections;

public class KeyStoreWitupTest {
    public static void main(String[] args) {
        testNullSecretKeyThrows();
        testValidSecretKeyEntry();
    }

    private static void testNullSecretKeyThrows() {
        try {
            new KeyStore.SecretKeyEntry(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("invalid null input")) {
                throw new RuntimeException("NullPointerException message unexpected: " + msg);
            }
        }
    }

    private static void testValidSecretKeyEntry() {
        byte[] keyBytes = new byte[] {0x01, 0x02, 0x03, 0x04};
        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key);
        if (entry.getSecretKey() == null) {
            throw new RuntimeException("SecretKeyEntry.getSecretKey() returned null");
        }
        if (!entry.getSecretKey().equals(key)) {
            throw new RuntimeException("SecretKeyEntry.getSecretKey() does not match input key");
        }
        if (entry.getAttributes() == null) {
            throw new RuntimeException("SecretKeyEntry.getAttributes() returned null");
        }
        if (!entry.getAttributes().isEmpty()) {
            throw new RuntimeException("SecretKeyEntry.getAttributes() expected to be empty");
        }
    }
}

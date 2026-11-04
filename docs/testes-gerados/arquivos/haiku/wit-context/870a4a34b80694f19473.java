/*
 * @test
 * @summary Tests KeyStore.SecretKeyEntry constructor null rejection and retention of the given key
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import javax.crypto.SecretKey;

public class KeyStoreWitupTest {

    public static void main(String[] args) throws Exception {
        testNullSecretKeyThrowsNpe();
        testNonNullSecretKeyIsReturned();
    }

    private static void testNullSecretKeyThrowsNpe() {
        try {
            new KeyStore.SecretKeyEntry(null);
            throw new AssertionError("NullPointerException expected for null secretKey");
        } catch (NullPointerException expected) {
            // expected behavior
        }
    }

    private static void testNonNullSecretKeyIsReturned() {
        SecretKey key = new SecretKey() {
            public String getAlgorithm() {
                return "AES";
            }
            public String getFormat() {
                return "RAW";
            }
            public byte[] getEncoded() {
                return new byte[16];
            }
        };
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key);
        if (entry.getSecretKey() != key) {
            throw new AssertionError("getSecretKey() should return the same key instance");
        }
    }
}

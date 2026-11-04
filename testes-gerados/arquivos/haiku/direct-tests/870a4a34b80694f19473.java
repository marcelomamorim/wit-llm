/*
 * @test
 * @summary Verify KeyStore.SecretKeyEntry constructor null handling and public accessors
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import java.util.Set;
import javax.crypto.SecretKey;

public class KeyStoreWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static final class FakeKey implements SecretKey {
        private final byte[] data;

        FakeKey(byte[] data) {
            this.data = data;
        }

        public String getAlgorithm() {
            return "AES";
        }

        public String getFormat() {
            return "RAW";
        }

        public byte[] getEncoded() {
            return data.clone();
        }
    }

    static void testNullInput() {
        boolean thrown = false;
        try {
            new KeyStore.SecretKeyEntry(null);
        } catch (NullPointerException e) {
            thrown = true;
            check("invalid null input".equals(e.getMessage()),
                  "unexpected NPE message: " + e.getMessage());
        }
        check(thrown, "null SecretKey must throw NullPointerException");
    }

    static void testReturnsSameKey() {
        SecretKey key = new FakeKey(new byte[] {1, 2, 3, 4});
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key);
        check(entry.getSecretKey() == key,
              "getSecretKey must return the instance passed to the constructor");
    }

    static void testAttributesEmpty() {
        SecretKey key = new FakeKey(new byte[] {5, 6, 7, 8});
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key);
        Set<?> attrs = entry.getAttributes();
        check(attrs != null, "getAttributes must not return null");
        check(attrs.isEmpty(), "new SecretKeyEntry must have empty attributes");
    }

    public static void main(String[] args) throws Exception {
        testNullInput();
        testReturnsSameKey();
        testAttributesEmpty();
    }
}

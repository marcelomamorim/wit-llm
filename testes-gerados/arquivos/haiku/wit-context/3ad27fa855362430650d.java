/*
 * @test
 * @summary Verifies null-input rejection and observable copy/unmodifiable behavior of KeyStore.SecretKeyEntry constructor
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;

public class KeyStoreWitupTest {

    static final class TestKey implements SecretKey {
        private static final long serialVersionUID = 1L;
        private final byte[] data;

        TestKey(byte[] data) {
            this.data = data.clone();
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

    static KeyStore.Entry.Attribute attr(final String name, final String value) {
        return new KeyStore.Entry.Attribute() {
            public String getName() {
                return name;
            }

            public String getValue() {
                return value;
            }
        };
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullSecretKeyRejected() {
        Set<KeyStore.Entry.Attribute> attrs = Collections.<KeyStore.Entry.Attribute>emptySet();
        try {
            new KeyStore.SecretKeyEntry(null, attrs);
            throw new RuntimeException("expected NullPointerException for null secretKey");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullAttributesRejected() {
        SecretKey key = new TestKey(new byte[] {1, 2, 3, 4});
        try {
            new KeyStore.SecretKeyEntry(key, null);
            throw new RuntimeException("expected NullPointerException for null attributes");
        } catch (NullPointerException expected) {
        }
    }

    static void testBothNullRejected() {
        try {
            new KeyStore.SecretKeyEntry(null, null);
            throw new RuntimeException("expected NullPointerException for both null");
        } catch (NullPointerException expected) {
        }
    }

    static void testValidInputReturnsKey() {
        SecretKey key = new TestKey(new byte[] {9, 8, 7});
        Set<KeyStore.Entry.Attribute> attrs = new HashSet<>();
        attrs.add(attr("name", "value"));
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key, attrs);
        check(entry.getSecretKey() == key, "getSecretKey should return the same key instance");
        check(entry.getAttributes().size() == 1, "attributes size should be 1");
    }

    static void testAttributesAreCopied() {
        SecretKey key = new TestKey(new byte[] {1});
        Set<KeyStore.Entry.Attribute> attrs = new HashSet<>();
        attrs.add(attr("a", "1"));
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key, attrs);
        attrs.add(attr("b", "2"));
        check(entry.getAttributes().size() == 1,
              "later changes to source set must not affect entry attributes");
    }

    static void testAttributesUnmodifiable() {
        SecretKey key = new TestKey(new byte[] {1});
        Set<KeyStore.Entry.Attribute> attrs = new HashSet<>();
        attrs.add(attr("a", "1"));
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key, attrs);
        try {
            entry.getAttributes().clear();
            throw new RuntimeException("entry attributes should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
        }
        check(entry.getAttributes().size() == 1, "attributes must remain unchanged");
    }

    public static void main(String[] args) {
        testNullSecretKeyRejected();
        testNullAttributesRejected();
        testBothNullRejected();
        testValidInputReturnsKey();
        testAttributesAreCopied();
        testAttributesUnmodifiable();
    }
}

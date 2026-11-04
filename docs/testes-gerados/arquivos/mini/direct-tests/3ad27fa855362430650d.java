/*
 * @test
 * @summary Test KeyStore.SecretKeyEntry constructor behavior
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import java.util.*;
import javax.crypto.SecretKey;

public class KeyStoreWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorRejectsNullSecretKey();
        testConstructorRejectsNullAttributes();
        testConstructorStoresAttributesUnmodifiable();
    }

    private static void testConstructorRejectsNullSecretKey() {
        try {
            new KeyStore.SecretKeyEntry(null, Collections.emptySet());
            throw new RuntimeException("Expected NullPointerException for null secretKey");
        } catch (NullPointerException e) {
            if (!"invalid null input".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testConstructorRejectsNullAttributes() {
        SecretKey dummyKey = new DummySecretKey();
        try {
            new KeyStore.SecretKeyEntry(dummyKey, null);
            throw new RuntimeException("Expected NullPointerException for null attributes");
        } catch (NullPointerException e) {
            if (!"invalid null input".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testConstructorStoresAttributesUnmodifiable() {
        SecretKey dummyKey = new DummySecretKey();
        Set<KeyStore.Entry.Attribute> attrs = new HashSet<>();
        KeyStore.Entry.Attribute attr = new KeyStore.Entry.Attribute() {
            public String getName() { return "name"; }
            public String getValue() { return "value"; }
        };
        attrs.add(attr);

        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(dummyKey, attrs);

        Set<KeyStore.Entry.Attribute> storedAttrs = entry.getAttributes();

        if (!storedAttrs.contains(attr)) {
            throw new RuntimeException("Attributes not stored correctly");
        }

        try {
            storedAttrs.add(attr);
            throw new RuntimeException("Attributes set should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Verify original set modification does not affect stored attributes
        attrs.clear();
        if (!entry.getAttributes().contains(attr)) {
            throw new RuntimeException("Stored attributes should be independent of original set");
        }
    }

    // Minimal dummy SecretKey implementation for testing
    private static class DummySecretKey implements SecretKey {
        public String getAlgorithm() { return "Dummy"; }
        public String getFormat() { return "RAW"; }
        public byte[] getEncoded() { return new byte[0]; }
    }
}

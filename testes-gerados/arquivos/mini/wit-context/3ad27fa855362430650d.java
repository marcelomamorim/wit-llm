/*
 * @test
 * @summary Test KeyStore.SecretKeyEntry constructor for null inputs and attribute immutability
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import javax.crypto.SecretKey;
import java.util.*;

public class KeyStoreWitupTest {
    public static void main(String[] args) throws Exception {
        testNullSecretKeyThrows();
        testNullAttributesThrows();
        testAttributesUnmodifiable();
    }

    private static void testNullSecretKeyThrows() {
        try {
            new KeyStore.SecretKeyEntry(null, new HashSet<KeyStore.Entry.Attribute>());
            throw new RuntimeException("Expected NullPointerException for null secretKey");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullAttributesThrows() {
        SecretKey dummyKey = new DummySecretKey();
        try {
            new KeyStore.SecretKeyEntry(dummyKey, null);
            throw new RuntimeException("Expected NullPointerException for null attributes");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testAttributesUnmodifiable() {
        SecretKey dummyKey = new DummySecretKey();
        Set<KeyStore.Entry.Attribute> attrs = new HashSet<KeyStore.Entry.Attribute>();
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(dummyKey, attrs);
        try {
            entry.getAttributes().add(new DummyAttribute());
            throw new RuntimeException("Expected UnsupportedOperationException when modifying attributes");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    // Minimal dummy SecretKey implementation for testing
    private static class DummySecretKey implements SecretKey {
        public String getAlgorithm() { return "Dummy"; }
        public String getFormat() { return "RAW"; }
        public byte[] getEncoded() { return new byte[0]; }
    }

    // Minimal dummy Attribute implementation for testing
    private static class DummyAttribute implements KeyStore.Entry.Attribute {
        public String getName() { return "dummy"; }
        public String getValue() { return "value"; }
    }
}

/* @test    @summary Test for KeyStore.SecretKeyEntry constructor with valid inputs    @run main KeyStoreWitupTest */

import java.security.KeyStore;
import java.security.KeyStore.SecretKeyEntry;
import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class KeyStoreWitupTest {

    public static void main(String[] args) {
        testSecretKeyEntryValid();
        testSecretKeyEntryNullSecretKey();
        testSecretKeyEntryNullAttributes();
    }

    static void testSecretKeyEntryValid() {
        SecretKey dummyKey = new DummySecretKey();
        Set<Attribute> attrs = new HashSet<>();
        attrs.add(new DummyAttribute());
        SecretKeyEntry entry = new KeyStore.SecretKeyEntry(dummyKey, attrs);
        assert entry != null : "SecretKeyEntry should not be null";
    }

    static void testSecretKeyEntryNullSecretKey() {
        Set<Attribute> attrs = new HashSet<>();
        attrs.add(new DummyAttribute());
        try {
            new KeyStore.SecretKeyEntry(null, attrs);
            assert false : "Expected NullPointerException for null secretKey";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testSecretKeyEntryNullAttributes() {
        SecretKey dummyKey = new DummySecretKey();
        try {
            new KeyStore.SecretKeyEntry(dummyKey, null);
            assert false : "Expected NullPointerException for null attributes";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static class DummySecretKey implements SecretKey {
        public String getAlgorithm() { return "DummyAlg"; }
        public String getFormat() { return "RAW"; }
        public byte[] getEncoded() { return new byte[0]; }
    }

    static class DummyAttribute implements Attribute {
        public String getName() { return "DummyAttribute"; }
        public Object getValue() { return null; }
    }

    interface Attribute {
        String getName();
        Object getValue();
    }
}

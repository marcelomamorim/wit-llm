/*
 * @test
 * @summary Deterministic tests for java.security.KeyStore.PrivateKeyEntry constructor
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Collections;

public class KeyStoreWitupTest {
    public static void main(String[] args) throws Exception {
        testNullPrivateKey();
        testNullChain();
        testEmptyChain();
        testValidConstruction();
        System.out.println("All tests passed.");
    }

    private static void testNullPrivateKey() {
        try {
            new KeyStore.PrivateKeyEntry(null, new Certificate[1]);
            throw new RuntimeException("Expected NullPointerException for null privateKey");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullChain() {
        try {
            PrivateKey dummyKey = new DummyPrivateKey();
            new KeyStore.PrivateKeyEntry(dummyKey, null);
            throw new RuntimeException("Expected NullPointerException for null chain");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyChain() {
        try {
            PrivateKey dummyKey = new DummyPrivateKey();
            Certificate[] emptyChain = new Certificate[0];
            new KeyStore.PrivateKeyEntry(dummyKey, emptyChain);
            throw new RuntimeException("Expected IllegalArgumentException for empty chain");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testValidConstruction() {
        PrivateKey dummyKey = new DummyPrivateKey();
        Certificate dummyCert = new DummyCertificate();
        Certificate[] chain = new Certificate[] { dummyCert };
        KeyStore.PrivateKeyEntry entry = new KeyStore.PrivateKeyEntry(dummyKey, chain);
        if (entry == null) {
            throw new RuntimeException("PrivateKeyEntry construction returned null");
        }
    }

    // Minimal dummy PrivateKey implementation for testing
    private static class DummyPrivateKey implements PrivateKey {
        private static final long serialVersionUID = 1L;
        public String getAlgorithm() { return "Dummy"; }
        public String getFormat() { return "DummyFormat"; }
        public byte[] getEncoded() { return new byte[0]; }
    }

    // Minimal dummy Certificate implementation for testing
    private static class DummyCertificate extends Certificate {
        private static final long serialVersionUID = 1L;
        protected DummyCertificate() {
            super("Dummy");
        }
        public byte[] getEncoded() {
            return new byte[0];
        }
        public void verify(java.security.PublicKey key) {
            // no-op
        }
        public void verify(java.security.PublicKey key, String sigProvider) {
            // no-op
        }
        public String toString() {
            return "DummyCertificate";
        }
        public java.security.PublicKey getPublicKey() {
            return null;
        }
    }
}

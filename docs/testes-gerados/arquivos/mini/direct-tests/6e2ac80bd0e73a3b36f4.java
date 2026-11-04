/*
 * @test
 * @summary Test KeyStore.PrivateKeyEntry constructor with various inputs
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Collections;

public class KeyStoreWitupTest {
    public static void main(String[] args) throws Exception {
        testNullPrivateKey();
        testEmptyCertificateChain();
        testCertificateChainWithNullElement();
        testValidPrivateKeyAndChain();
    }

    private static void testNullPrivateKey() {
        try {
            new KeyStore.PrivateKeyEntry(null, new Certificate[0]);
            throw new RuntimeException("Expected NullPointerException for null privateKey");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testEmptyCertificateChain() {
        PrivateKey dummyKey = new DummyPrivateKey();
        Certificate[] emptyChain = new Certificate[0];
        KeyStore.PrivateKeyEntry entry = new KeyStore.PrivateKeyEntry(dummyKey, emptyChain);
        if (entry.getPrivateKey() != dummyKey) {
            throw new RuntimeException("PrivateKey mismatch");
        }
        Certificate[] chain = entry.getCertificateChain();
        if (chain == null || chain.length != 0) {
            throw new RuntimeException("Expected empty certificate chain");
        }
    }

    private static void testCertificateChainWithNullElement() {
        PrivateKey dummyKey = new DummyPrivateKey();
        Certificate[] chainWithNull = new Certificate[] { null };
        try {
            new KeyStore.PrivateKeyEntry(dummyKey, chainWithNull);
            throw new RuntimeException("Expected NullPointerException for null certificate in chain");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testValidPrivateKeyAndChain() throws CertificateException {
        PrivateKey dummyKey = new DummyPrivateKey();
        Certificate dummyCert = new DummyCertificate();
        Certificate[] chain = new Certificate[] { dummyCert };
        KeyStore.PrivateKeyEntry entry = new KeyStore.PrivateKeyEntry(dummyKey, chain);
        if (entry.getPrivateKey() != dummyKey) {
            throw new RuntimeException("PrivateKey mismatch");
        }
        Certificate[] returnedChain = entry.getCertificateChain();
        if (returnedChain == null || returnedChain.length != 1 || returnedChain[0] != dummyCert) {
            throw new RuntimeException("Certificate chain mismatch");
        }
    }

    // Dummy PrivateKey implementation for testing
    private static class DummyPrivateKey implements PrivateKey {
        private static final long serialVersionUID = 1L;
        public String getAlgorithm() { return "Dummy"; }
        public String getFormat() { return "None"; }
        public byte[] getEncoded() { return null; }
    }

    // Dummy Certificate implementation for testing
    private static class DummyCertificate extends Certificate {
        private static final long serialVersionUID = 1L;
        protected DummyCertificate() {
            super("Dummy");
        }
        public byte[] getEncoded() {
            return new byte[0];
        }
        public void verify(java.security.PublicKey key) {
        }
        public void verify(java.security.PublicKey key, String sigProvider) {
        }
        public String toString() {
            return "DummyCertificate";
        }
        public java.security.PublicKey getPublicKey() {
            return null;
        }
    }
}

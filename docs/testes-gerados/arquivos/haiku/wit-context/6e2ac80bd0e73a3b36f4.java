/*
 * @test
 * @summary Tests observable behavior of KeyStore.PrivateKeyEntry(PrivateKey, Certificate[]) constructor
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

public class KeyStoreWitupTest {

    public static void main(String[] args) throws Exception {
        PrivateKey key = new PrivateKey() {
            public String getAlgorithm() { return "Test"; }
            public String getFormat() { return "RAW"; }
            public byte[] getEncoded() { return new byte[0]; }
        };

        Certificate cert = new Certificate("X.509") {
            public byte[] getEncoded() { return new byte[0]; }
            public void verify(PublicKey key) { }
            public void verify(PublicKey key, String sigProvider) { }
            public String toString() { return "TestCert"; }
            public PublicKey getPublicKey() { return null; }
        };

        expectThrows(NullPointerException.class,
            () -> new KeyStore.PrivateKeyEntry(null, new Certificate[] { cert }));

        expectThrows(NullPointerException.class,
            () -> new KeyStore.PrivateKeyEntry(key, null));

        expectThrows(IllegalArgumentException.class,
            () -> new KeyStore.PrivateKeyEntry(key, new Certificate[0]));

        KeyStore.PrivateKeyEntry entry =
            new KeyStore.PrivateKeyEntry(key, new Certificate[] { cert });
        if (entry.getPrivateKey() != key) {
            throw new RuntimeException("private key not preserved");
        }
        if (entry.getCertificateChain().length != 1) {
            throw new RuntimeException("unexpected chain length");
        }
        if (!entry.getAttributes().isEmpty()) {
            throw new RuntimeException("attributes should be empty");
        }
    }

    private static void expectThrows(Class<? extends Throwable> expected, Runnable r) {
        try {
            r.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new RuntimeException("unexpected exception: " + t, t);
        }
        throw new RuntimeException("expected " + expected.getName());
    }
}

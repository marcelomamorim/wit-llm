/*
 * @test
 * @summary KeyStore.PrivateKeyEntry two-argument constructor exposes the given key and a defensive copy of the chain
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Set;

public class KeyStoreWitupTest {

    static final class DummyKey implements PrivateKey {
        private final String alg;
        DummyKey(String alg) { this.alg = alg; }
        public String getAlgorithm() { return alg; }
        public String getFormat() { return "RAW"; }
        public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
    }

    static final class DummyCert extends Certificate {
        private final byte[] enc;
        DummyCert(byte[] enc) {
            super("X.509");
            this.enc = enc;
        }
        public byte[] getEncoded() { return enc.clone(); }
        public void verify(PublicKey key) { }
        public void verify(PublicKey key, String sigProvider) { }
        public String toString() { return "DummyCert"; }
        public PublicKey getPublicKey() { return null; }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        DummyKey key = new DummyKey("RSA");
        DummyCert cert = new DummyCert(new byte[] {9, 9});
        Certificate[] chain = new Certificate[] { cert };

        PrivateKeyEntry entry = new PrivateKeyEntry(key, chain);

        check(entry.getPrivateKey() == key, "private key not preserved");

        Certificate[] got = entry.getCertificateChain();
        check(got != null, "chain is null");
        check(got.length == 1, "chain length mismatch: " + got.length);
        check(got[0] == cert, "chain element mismatch");

        Set<?> attrs = entry.getAttributes();
        check(attrs != null && attrs.isEmpty(), "attributes should be empty");

        Certificate[] mutable = new Certificate[] { cert };
        PrivateKeyEntry copyEntry = new PrivateKeyEntry(key, mutable);
        mutable[0] = null;
        Certificate[] after = copyEntry.getCertificateChain();
        check(after.length == 1 && after[0] == cert, "chain was not defensively copied");
    }
}

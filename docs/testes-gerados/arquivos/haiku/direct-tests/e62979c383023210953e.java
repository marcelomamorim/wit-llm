/*
 * @test
 * @summary Verifies that Signer.setKeyPair publishes the public key (via setPublicKey) and stores the private key, observable through getPublicKey and getPrivateKey.
 * @run main SignerWitupTest
 */

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signer;

@SuppressWarnings({"removal", "deprecation"})
public class SignerWitupTest {

    static class MySigner extends Signer {
        private static final long serialVersionUID = 1L;
        MySigner(String name) {
            super(name);
        }
    }

    static PublicKey newPublicKey(final String alg) {
        return new PublicKey() {
            private static final long serialVersionUID = 1L;
            public String getAlgorithm() { return alg; }
            public String getFormat() { return "X.509"; }
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
    }

    static PrivateKey newPrivateKey(final String alg) {
        return new PrivateKey() {
            private static final long serialVersionUID = 1L;
            public String getAlgorithm() { return alg; }
            public String getFormat() { return "PKCS#8"; }
            public byte[] getEncoded() { return new byte[] {4, 5, 6}; }
        };
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        MySigner s = new MySigner("alice");
        check("alice".equals(s.getName()), "name not preserved");
        check(s.getPrivateKey() == null, "private key should be null before setKeyPair");
        check(s.getPublicKey() == null, "public key should be null before setKeyPair");

        PublicKey pub1 = newPublicKey("DSA");
        PrivateKey priv1 = newPrivateKey("DSA");
        s.setKeyPair(new KeyPair(pub1, priv1));
        check(s.getPublicKey() == pub1, "setKeyPair did not publish the public key");
        check(s.getPrivateKey() == priv1, "setKeyPair did not store the private key");

        PublicKey pub2 = newPublicKey("DSA");
        PrivateKey priv2 = newPrivateKey("DSA");
        s.setKeyPair(new KeyPair(pub2, priv2));
        check(s.getPublicKey() == pub2, "second setKeyPair did not replace the public key");
        check(s.getPrivateKey() == priv2, "second setKeyPair did not replace the private key");
    }
}

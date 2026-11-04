/*
 * @test
 * @summary Verifies the two-argument PKCS10 constructor accepts a public key and attributes without throwing, including null arguments.
 * @modules java.base/sun.security.pkcs10
 * @run main PKCS10WitupTest
 */

import java.security.PublicKey;
import sun.security.pkcs10.PKCS10;

public class PKCS10WitupTest {

    static final class DummyPublicKey implements PublicKey {
        private static final long serialVersionUID = 1L;

        public String getAlgorithm() {
            return "RSA";
        }

        public String getFormat() {
            return "X.509";
        }

        public byte[] getEncoded() {
            return new byte[] {1, 2, 3};
        }
    }

    public static void main(String[] args) {
        PublicKey key = new DummyPublicKey();

        PKCS10 withKeyAndNullAttrs = new PKCS10(key, null);
        if (withKeyAndNullAttrs == null) {
            throw new RuntimeException("constructor returned null for non-null key");
        }

        PKCS10 withNullKeyAndNullAttrs = new PKCS10(null, null);
        if (withNullKeyAndNullAttrs == null) {
            throw new RuntimeException("constructor returned null for null arguments");
        }

        PKCS10 first = new PKCS10(key, null);
        PKCS10 second = new PKCS10(key, null);
        if (first == second) {
            throw new RuntimeException("constructor must create a new instance on each call");
        }
    }
}

/*
 * @test
 * @summary Basic observable behavior of Identity.setPublicKey
 * @run main IdentityWitupTest
 */

import java.security.Identity;
import java.security.PublicKey;

public class IdentityWitupTest {

    static final class TestKey implements PublicKey {
        private final String alg;

        TestKey(String alg) {
            this.alg = alg;
        }

        public String getAlgorithm() {
            return alg;
        }

        public String getFormat() {
            return "RAW";
        }

        public byte[] getEncoded() {
            return new byte[] {1, 2, 3};
        }
    }

    @SuppressWarnings("removal")
    static final class TestIdentity extends Identity {
        TestIdentity() {
            super("test-identity");
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    @SuppressWarnings("removal")
    public static void main(String[] args) throws Exception {
        TestIdentity id = new TestIdentity();

        TestKey k1 = new TestKey("ALG1");
        id.setPublicKey(k1);
        check(id.getPublicKey() == k1, "public key not stored");

        TestKey k2 = new TestKey("ALG2");
        id.setPublicKey(k2);
        check(id.getPublicKey() == k2, "public key not replaced");
        check("ALG2".equals(id.getPublicKey().getAlgorithm()), "wrong algorithm after replace");

        id.setPublicKey(null);
        check(id.getPublicKey() == null, "null public key not stored");

        id.setPublicKey(k1);
        check(id.getPublicKey() == k1, "public key not restored after null");

        System.out.println("PASSED");
    }
}

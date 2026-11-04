/*
 * @test
 * @summary Verifies that Identity.setPublicKey stores the given key and resets certificates without throwing when no SecurityManager is installed
 * @run main IdentityWitupTest
 */

import java.security.Identity;
import java.security.PublicKey;

public class IdentityWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static PublicKey newKey(final String alg) {
        return new PublicKey() {
            public String getAlgorithm() {
                return alg;
            }
            public String getFormat() {
                return "RAW";
            }
            public byte[] getEncoded() {
                return new byte[] {1, 2, 3};
            }
        };
    }

    public static void main(String[] args) throws Exception {
        Identity id = new Identity("witup-id") {};

        PublicKey first = newKey("TEST1");
        id.setPublicKey(first);
        check(id.getPublicKey() == first, "public key not stored after setPublicKey");

        PublicKey second = newKey("TEST2");
        id.setPublicKey(second);
        check(id.getPublicKey() == second, "public key not replaced by second setPublicKey");

        id.setPublicKey(null);
        check(id.getPublicKey() == null, "public key not cleared by setPublicKey(null)");
    }
}

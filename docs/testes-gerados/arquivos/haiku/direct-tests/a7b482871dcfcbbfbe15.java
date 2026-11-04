/*
 * @test
 * @summary Verifies that the deprecated default constructor of AlgIdDSA creates an instance whose DSA parameters P, Q and G are null until deserialization.
 * @modules java.base/sun.security.x509
 * @run main AlgIdDSAWitupTest
 */

import sun.security.x509.AlgIdDSA;

public class AlgIdDSAWitupTest {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {
        AlgIdDSA alg = new AlgIdDSA();

        check(alg != null, "instance must not be null");
        check(alg.getP() == null, "getP() must be null for default constructor");
        check(alg.getQ() == null, "getQ() must be null for default constructor");
        check(alg.getG() == null, "getG() must be null for default constructor");

        AlgIdDSA other = new AlgIdDSA();
        check(other != alg, "each invocation must create a new instance");
        check(other.getP() == null && other.getQ() == null && other.getG() == null,
              "second instance must also have null parameters");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

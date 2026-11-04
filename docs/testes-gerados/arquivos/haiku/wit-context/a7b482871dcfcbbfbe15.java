/*
 * @test
 * @summary Verify that the deprecated no-arg AlgIdDSA constructor leaves P, Q and G unset
 * @modules java.base/sun.security.x509
 * @run main AlgIdDSAWitupTest
 */
import sun.security.x509.AlgIdDSA;

public class AlgIdDSAWitupTest {

    public static void main(String[] args) {
        AlgIdDSA alg = newAlgIdDSA();
        check(alg != null, "instance is null");
        check(alg.getP() == null, "P should be null before deserialization");
        check(alg.getQ() == null, "Q should be null before deserialization");
        check(alg.getG() == null, "G should be null before deserialization");
    }

    @SuppressWarnings("deprecation")
    private static AlgIdDSA newAlgIdDSA() {
        return new AlgIdDSA();
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

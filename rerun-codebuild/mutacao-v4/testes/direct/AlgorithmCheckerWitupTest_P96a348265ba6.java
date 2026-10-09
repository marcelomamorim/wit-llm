/* @test
   @summary Verifies the init method behavior of AlgorithmChecker.
   @run main AlgorithmCheckerWitupTest_P96a348265ba6
 */
import java.security.cert.CertPathValidatorException;
import sun.security.provider.certpath.AlgorithmChecker;

public class AlgorithmCheckerWitupTest_P96a348265ba6 {
    public static void main(String[] args) {
        testInitWithForward();
        testInitWithoutForward();
    }

    private static void testInitWithForward() {
        AlgorithmChecker checker = new AlgorithmChecker(null, "variant");
        try {
            checker.init(true);
            throw new AssertionError("Expected CertPathValidatorException for forward mode");
        } catch (CertPathValidatorException e) {
            // expected exception
            if (!e.getMessage().contains("forward checking not supported")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }

    private static void testInitWithoutForward() {
        AlgorithmChecker checker = new AlgorithmChecker(null, "variant");
        try {
            checker.init(false);
            // Should set prevPubKey to null or trustedPubKey
            // No exception expected
        } catch (CertPathValidatorException e) {
            throw new AssertionError("Did not expect exception here", e);
        }
    }
}

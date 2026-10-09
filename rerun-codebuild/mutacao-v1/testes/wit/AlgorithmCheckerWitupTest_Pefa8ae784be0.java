/* @test    @summary Test for AlgorithmChecker.init(boolean) method behavior.    @run main AlgorithmCheckerWitupTest_Pefa8ae784be0 */

import sun.security.provider.certpath.AlgorithmChecker;
import java.security.cert.CertPathValidatorException;

public class AlgorithmCheckerWitupTest_Pefa8ae784be0 {

    public static void main(String[] args) throws Exception {
        testInitWithForward();
        testInitWithBackward();
    }

    private static void testInitWithForward() {
        AlgorithmChecker ac = createAlgorithmChecker();
        boolean forward = true;
        try {
            ac.init(forward);
            throw new AssertionError("Expected CertPathValidatorException not thrown for forward=true");
        } catch (CertPathValidatorException e) {
            if (!"forward checking not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }

    private static void testInitWithBackward() {
        AlgorithmChecker ac = createAlgorithmChecker();
        boolean forward = false;
        try {
            ac.init(forward);
            // Should set prevPubKey to null or trustedPubKey without exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for backward init: " + e, e);
        }
    }

    private static AlgorithmChecker createAlgorithmChecker() {
        // Create a dummy AlgorithmChecker with minimal setup
        // Since constructor is complex, use reflection or dummy subclass if needed
        // For simplicity, create an anonymous subclass overriding init if necessary
        return new AlgorithmChecker(null, null, null, null, "") {
            @Override
            public void init(boolean forward) throws CertPathValidatorException {
                super.init(forward);
            }
        };
    }
}

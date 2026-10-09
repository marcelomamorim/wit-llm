/*
 * @test
 * @summary Test AlgorithmChecker.init() method for backward compatibility and exception throwing
 * @run main AlgorithmCheckerWitupTest_Pefa8ae784be0
 */

import java.security.cert.CertPathValidatorException;

public class AlgorithmCheckerWitupTest_Pefa8ae784be0 {

    public static void main(String[] args) {
        testInitMethodForwardFalse();
        testInitMethodForwardTrueThrows();
    }

    private static void testInitMethodForwardFalse() {
        sun.security.provider.certpath.AlgorithmChecker checker = new sun.security.provider.certpath.AlgorithmChecker();
        try {
            checker.init(false);
        } catch (CertPathValidatorException e) {
            throw new RuntimeException("Unexpected exception in init(false)", e);
        }
        if (checker.prevPubKey != null) {
            throw new RuntimeException("prevPubKey should be null when trustedPubKey is null and forward is false");
        }
    }

    private static void testInitMethodForwardTrueThrows() {
        sun.security.provider.certpath.AlgorithmChecker checker = new sun.security.provider.certpath.AlgorithmChecker();
        boolean exceptionThrown = false;
        try {
            checker.init(true);
        } catch (CertPathValidatorException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new RuntimeException("Expected CertPathValidatorException to be thrown for forward=true");
        }
    }
}

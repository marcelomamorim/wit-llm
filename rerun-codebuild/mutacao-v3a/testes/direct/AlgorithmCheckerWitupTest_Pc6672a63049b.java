/*
 * @test
 * @summary Test init() method of AlgorithmChecker to ensure it handles forward and backward initialization correctly.
 * @run main AlgorithmCheckerWitupTest_Pc6672a63049b
 */

import java.security.cert.CertPathValidatorException;

public class AlgorithmCheckerWitupTest_Pc6672a63049b {

    public static void main(String[] args) {
        testInitBackwardWithoutTrustedPubKey();
        testInitForwardThrowsException();
    }

    public static void testInitBackwardWithoutTrustedPubKey() {
        // Create a mock AlgorithmChecker with null trustedPubKey
        AlgorithmChecker checker = new AlgorithmChecker(null, null, null, null, "variant");
        try {
            // Should set prevPubKey to null without exception
            checker.init(false);
            System.out.println("testInitBackwardWithoutTrustedPubKey passed");
        } catch (CertPathValidatorException e) {
            throw new AssertionError("Unexpected exception in backward init", e);
        }
    }

    public static void testInitForwardThrowsException() {
        // Create a mock AlgorithmChecker with dummy parameters
        AlgorithmChecker checker = new AlgorithmChecker(null, null, null, null, "variant");
        try {
            // Should throw CertPathValidatorException for forward mode
            checker.init(true);
            throw new AssertionError("Expected exception not thrown");
        } catch (CertPathValidatorException e) {
            if ("forward checking not supported".equals(e.getMessage())) {
                System.out.println("testInitForwardThrowsException passed");
            } else {
                throw new AssertionError("Unexpected exception message", e);
            }
        }
    }
}

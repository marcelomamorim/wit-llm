/*
 * @test
 * @summary Test AlgorithmChecker.init(boolean) behavior
 * @run main AlgorithmCheckerWitupTest_P191cff510238
 */

import sun.security.provider.certpath.AlgorithmChecker;
import java.security.cert.CertPathValidatorException;
import java.security.cert.TrustAnchor;
import java.security.AlgorithmConstraints;
import java.util.Date;

public class AlgorithmCheckerWitupTest_P191cff510238 {
    public static void main(String[] args) throws Exception {
        testInitWithForwardFalseAndTrustedPubKey();
        testInitWithForwardFalseAndNoTrustedPubKey();
        testInitWithForwardTrueThrows();
    }

    private static void testInitWithForwardFalseAndTrustedPubKey() throws Exception {
        TrustAnchor anchor = new TrustAnchor(
            new sun.security.x509.X509CertImpl(new byte[0]), null);
        AlgorithmChecker checker = new AlgorithmChecker(anchor, "variant");
        // forcibly set trustedPubKey to non-null via constructor
        checker.init(false);
        // prevPubKey should be set to trustedPubKey (non-null)
        if (checker.prevPubKey == null) {
            throw new RuntimeException("prevPubKey should be set to trustedPubKey when forward is false and trustedPubKey is non-null");
        }
    }

    private static void testInitWithForwardFalseAndNoTrustedPubKey() throws Exception {
        // Use constructor with null TrustAnchor to get trustedPubKey null
        AlgorithmChecker checker = new AlgorithmChecker((TrustAnchor) null, "variant");
        checker.init(false);
        if (checker.prevPubKey != null) {
            throw new RuntimeException("prevPubKey should be null when forward is false and trustedPubKey is null");
        }
    }

    private static void testInitWithForwardTrueThrows() throws Exception {
        AlgorithmChecker checker = new AlgorithmChecker((TrustAnchor) null, "variant");
        try {
            checker.init(true);
            throw new RuntimeException("Expected CertPathValidatorException not thrown");
        } catch (CertPathValidatorException e) {
            String msg = e.getMessage();
            if (!"forward checking not supported".equals(msg)) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}

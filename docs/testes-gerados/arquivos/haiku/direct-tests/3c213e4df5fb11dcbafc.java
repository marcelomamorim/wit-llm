/*
 * @test
 * @summary Verify AlgorithmChecker.init(boolean) reverse-mode initialization and forward-mode rejection
 * @modules java.base/sun.security.provider.certpath
 * @run main AlgorithmCheckerWitupTest
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.TrustAnchor;

import sun.security.provider.certpath.AlgorithmChecker;

public class AlgorithmCheckerWitupTest {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        TrustAnchor anchor = new TrustAnchor("CN=WitupTest", kp.getPublic(), null);

        testReverseInitSucceeds(anchor);
        testReverseInitIsRepeatable(anchor);
        testForwardInitRejected(anchor);
        testForwardInitRejectedWithDateConstructor(anchor);
    }

    private static void testReverseInitSucceeds(TrustAnchor anchor) throws Exception {
        AlgorithmChecker checker = new AlgorithmChecker(anchor, "generic");
        checker.init(false);
    }

    private static void testReverseInitIsRepeatable(TrustAnchor anchor) throws Exception {
        AlgorithmChecker checker = new AlgorithmChecker(anchor, "generic");
        checker.init(false);
        checker.init(false);
    }

    private static void testForwardInitRejected(TrustAnchor anchor) throws Exception {
        AlgorithmChecker checker = new AlgorithmChecker(anchor, "generic");
        boolean thrown = false;
        try {
            checker.init(true);
        } catch (CertPathValidatorException e) {
            thrown = true;
            if (!"forward checking not supported".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("init(true) did not throw CertPathValidatorException");
        }
    }

    private static void testForwardInitRejectedWithDateConstructor(TrustAnchor anchor) throws Exception {
        AlgorithmChecker checker = new AlgorithmChecker(anchor, new java.util.Date(), "generic");
        boolean thrown = false;
        try {
            checker.init(true);
        } catch (CertPathValidatorException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("init(true) did not throw with Date constructor");
        }
    }
}

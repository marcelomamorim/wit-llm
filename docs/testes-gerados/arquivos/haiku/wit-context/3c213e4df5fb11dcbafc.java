/*
 * @test
 * @summary Verify AlgorithmChecker.init(boolean) accepts reverse mode and rejects forward mode with CertPathValidatorException
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

        TrustAnchor anchor = new TrustAnchor("CN=Witup Test CA", kp.getPublic(), null);
        AlgorithmChecker checker = new AlgorithmChecker(anchor, "PKIX");

        checker.init(false);
        checker.init(false);

        boolean thrown = false;
        String message = null;
        try {
            checker.init(true);
        } catch (CertPathValidatorException e) {
            thrown = true;
            message = e.getMessage();
        }
        if (!thrown) {
            throw new RuntimeException("init(true) did not throw CertPathValidatorException");
        }
        if (!"forward checking not supported".equals(message)) {
            throw new RuntimeException("Unexpected message: " + message);
        }
    }
}

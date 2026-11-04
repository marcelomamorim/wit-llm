/*
 * @test
 * @summary Test sun.security.provider.certpath.KeyChecker.init(boolean) method
 * @run main KeyCheckerWitupTest
 */

import java.security.cert.CertPathValidatorException;
import sun.security.provider.certpath.KeyChecker;

public class KeyCheckerWitupTest {
    public static void main(String[] args) throws Exception {
        // Create KeyChecker instance with dummy parameters
        // certPathLen = 1, targetConstraints = null (allowed by constructor signature)
        KeyChecker checker = new KeyChecker(1, null);

        // Test init(false) does not throw and sets internal state
        try {
            checker.init(false);
        } catch (CertPathValidatorException e) {
            throw new RuntimeException("init(false) should not throw", e);
        }

        // Test init(true) throws CertPathValidatorException with expected message
        try {
            checker.init(true);
            throw new RuntimeException("init(true) did not throw expected CertPathValidatorException");
        } catch (CertPathValidatorException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("forward checking not supported")) {
                throw new RuntimeException("init(true) threw CertPathValidatorException but with unexpected message: " + msg);
            }
        }
    }
}

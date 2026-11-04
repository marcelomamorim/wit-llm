/*
 * @test
 * @summary Test sun.security.provider.certpath.ConstraintsChecker.init(boolean) method
 * @run main ConstraintsCheckerWitupTest
 */

import sun.security.provider.certpath.ConstraintsChecker;
import java.security.cert.CertPathValidatorException;

public class ConstraintsCheckerWitupTest {
    public static void main(String[] args) throws Exception {
        // Create ConstraintsChecker with arbitrary certPathLength
        ConstraintsChecker checker = new ConstraintsChecker(5);

        // Test init(false) does not throw and sets internal state
        try {
            checker.init(false);
        } catch (CertPathValidatorException e) {
            throw new RuntimeException("init(false) should not throw", e);
        }

        // Test init(true) throws CertPathValidatorException with expected message
        boolean thrown = false;
        try {
            checker.init(true);
        } catch (CertPathValidatorException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("forward checking not supported")) {
                throw new RuntimeException("Unexpected exception message: " + msg, e);
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected CertPathValidatorException not thrown by init(true)");
        }
    }
}

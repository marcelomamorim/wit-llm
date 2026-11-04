/*
 * @test
 * @summary Test sun.security.provider.certpath.ConstraintsChecker.init(boolean) method
 * @run main ConstraintsCheckerWitupTest
 */

import sun.security.provider.certpath.ConstraintsChecker;
import java.security.cert.CertPathValidatorException;

public class ConstraintsCheckerWitupTest {
    public static void main(String[] args) throws Exception {
        ConstraintsChecker checker = new ConstraintsChecker(5);

        // Test init with forward == false: should initialize internal state without exception
        try {
            checker.init(false);
        } catch (CertPathValidatorException e) {
            throw new RuntimeException("init(false) should not throw", e);
        }

        // Test init with forward == true: should throw CertPathValidatorException
        boolean thrown = false;
        try {
            checker.init(true);
        } catch (CertPathValidatorException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("forward checking not supported")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }
        if (!thrown) {
            throw new RuntimeException("init(true) did not throw CertPathValidatorException as expected");
        }
    }
}

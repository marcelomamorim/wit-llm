/*
 * @test
 * @summary Test for AlgorithmChecker.init(boolean) method behavior.
 * @run main AlgorithmCheckerWitupTest
 */

import sun.security.provider.certpath.AlgorithmChecker;
import java.security.cert.CertPathValidatorException;

public class AlgorithmCheckerWitupTest {

    public static void main(String[] args) {
        testInitWithForward();
        testInitWithBackward();
    }

    private static void testInitWithForward() {
        AlgorithmChecker checker = new AlgorithmChecker(null, "variant");
        boolean forward = true;
        try {
            checker.init(forward);
            // Expecting CertPathValidatorException due to forward mode not supported
            System.err.println("FAIL: Expected CertPathValidatorException for forward mode");
        } catch (CertPathValidatorException e) {
            if ("forward checking not supported".equals(e.getMessage())) {
                System.out.println("PASS: Correct exception thrown for forward mode");
            } else {
                System.err.println("FAIL: Unexpected message: " + e.getMessage());
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testInitWithBackward() {
        AlgorithmChecker checker = new AlgorithmChecker(null, "variant");
        boolean backward = false;
        try {
            checker.init(backward);
            System.out.println("PASS: init with backward mode completed without exception");
        } catch (CertPathValidatorException e) {
            System.err.println("FAIL: Unexpected exception for backward mode: " + e.getMessage());
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }
}

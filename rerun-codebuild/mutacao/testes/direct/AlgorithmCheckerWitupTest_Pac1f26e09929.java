/*
 * @test
 * @summary Test AlgorithmChecker.init method for backward initialization behavior
 * @run main AlgorithmCheckerWitupTest_Pac1f26e09929
 */

import java.security.cert.CertPathValidatorException;
import sun.security.provider.certpath.AlgorithmChecker;

public class AlgorithmCheckerWitupTest_Pac1f26e09929 {

    public static void main(String[] args) {
        testInitWithForward();
        testInitWithBackward();
    }

    private static void testInitWithForward() {
        AlgorithmChecker checker = new AlgorithmChecker(null, null, null, null, "testVariant");
        try {
            checker.init(true);
            System.out.println("PASS: init(true) did not throw exception as expected.");
        } catch (CertPathValidatorException e) {
            System.out.println("FAIL: init(true) threw exception: " + e);
        }
    }

    private static void testInitWithBackward() {
        AlgorithmChecker checker = new AlgorithmChecker(null, null, null, null, "testVariant");
        try {
            checker.init(false);
            System.out.println("FAIL: init(false) did not throw exception as expected.");
        } catch (CertPathValidatorException e) {
            System.out.println("PASS: init(false) threw expected exception: " + e);
        }
    }
}

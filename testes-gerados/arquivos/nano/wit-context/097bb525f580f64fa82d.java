/*
 * @test
 * @summary Test for ConstraintsChecker.init(boolean)
 * @run main ConstraintsCheckerWitupTest
 */

import sun.security.provider.certpath.ConstraintsChecker;
import java.security.cert.CertPathValidatorException;

public class ConstraintsCheckerWitupTest {

    public static void main(String[] args) {
        testInitWithForward();
        testInitWithBackward();
    }

    private static void testInitWithForward() {
        ConstraintsChecker checker = new ConstraintsChecker(3);
        boolean forward = true;
        try {
            checker.init(forward);
            System.out.println("testInitWithForward: FAILED - no exception thrown");
        } catch (CertPathValidatorException e) {
            if (e.getMessage().contains("forward checking not supported")) {
                System.out.println("testInitWithForward: PASSED");
            } else {
                System.out.println("testInitWithForward: FAILED - wrong exception message");
            }
        } catch (Throwable t) {
            System.out.println("testInitWithForward: FAILED - unexpected exception: " + t);
        }
    }

    private static void testInitWithBackward() {
        ConstraintsChecker checker = new ConstraintsChecker(3);
        boolean forward = false;
        try {
            checker.init(forward);
            System.out.println("testInitWithBackward: PASSED");
        } catch (Throwable t) {
            System.out.println("testInitWithBackward: FAILED - unexpected exception: " + t);
        }
    }
}

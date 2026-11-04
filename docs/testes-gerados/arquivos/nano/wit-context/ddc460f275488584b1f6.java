/* @test    @summary Test for KeyChecker.init(boolean) method behavior.
 * @run main KeyCheckerWitupTest
 */

import sun.security.provider.certpath.KeyChecker;
import java.security.cert.CertPathValidatorException;

public class KeyCheckerWitupTest {

    public static void main(String[] args) {
        testInitWithForward();
        testInitWithoutForward();
    }

    private static void testInitWithForward() {
        KeyChecker checker = new KeyChecker(3, null);
        boolean forward = true;
        try {
            checker.init(forward);
            System.out.println("testInitWithForward: FAILED - Expected exception not thrown");
        } catch (CertPathValidatorException e) {
            String message = e.getMessage();
            if (message != null && message.contains("forward checking not supported")) {
                System.out.println("testInitWithForward: PASSED");
            } else {
                System.out.println("testInitWithForward: FAILED - Unexpected exception message");
            }
        } catch (Throwable t) {
            System.out.println("testInitWithForward: FAILED - Unexpected exception type: " + t);
        }
    }

    private static void testInitWithoutForward() {
        KeyChecker checker = new KeyChecker(3, null);
        boolean forward = false;
        try {
            checker.init(forward);
            System.out.println("testInitWithoutForward: PASSED");
        } catch (Throwable t) {
            System.out.println("testInitWithoutForward: FAILED - Unexpected exception: " + t);
        }
    }
}

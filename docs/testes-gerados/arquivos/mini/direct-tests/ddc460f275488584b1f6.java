/*
 * @test
 * @summary Test sun.security.provider.certpath.KeyChecker.init(boolean) method
 * @run main KeyCheckerWitupTest
 */

import java.security.cert.CertPathValidatorException;
import java.security.cert.CertSelector;
import sun.security.provider.certpath.KeyChecker;

public class KeyCheckerWitupTest {

    public static void main(String[] args) throws Exception {
        testInitWithForwardFalse();
        testInitWithForwardTrueThrows();
    }

    private static void testInitWithForwardFalse() throws Exception {
        // Create KeyChecker with certPathLen=5 and null targetConstraints
        KeyChecker checker = new KeyChecker(5, null);
        // Call init with forward=false, should set remainingCerts to certPathLen without exception
        checker.init(false);
        // No direct getter for remainingCerts, so test no exception and isForwardCheckingSupported returns false
        if (checker.isForwardCheckingSupported()) {
            throw new RuntimeException("Expected isForwardCheckingSupported() to return false");
        }
    }

    private static void testInitWithForwardTrueThrows() {
        KeyChecker checker = new KeyChecker(3, null);
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

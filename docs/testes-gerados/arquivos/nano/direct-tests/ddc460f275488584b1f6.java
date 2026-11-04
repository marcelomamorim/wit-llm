/* @test    @summary Test for KeyChecker.init method in forward and backward modes    @run main KeyCheckerWitupTest */

import sun.security.provider.certpath.KeyChecker;
import java.security.cert.CertPathValidatorException;

public class KeyCheckerWitupTest {

    public static void main(String[] args) {
        testInitBackward();
        testInitForwardThrows();
    }

    private static void testInitBackward() {
        KeyChecker checker = new KeyChecker(5, null);
        try {
            checker.init(false);
            System.out.println("testInitBackward passed");
        } catch (CertPathValidatorException e) {
            System.err.println("testInitBackward failed: " + e);
        }
    }

    private static void testInitForwardThrows() {
        KeyChecker checker = new KeyChecker(5, null);
        try {
            checker.init(true);
            System.err.println("testInitForwardThrows failed: no exception thrown");
        } catch (CertPathValidatorException e) {
            System.out.println("testInitForwardThrows passed");
        }
    }
}

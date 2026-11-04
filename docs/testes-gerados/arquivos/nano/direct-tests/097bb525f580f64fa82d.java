/* @test    @summary Test ConstraintsChecker.init method for backward initialization and exception on forward check    @run main ConstraintsCheckerWitupTest */

import java.security.cert.CertPathValidatorException;
import sun.security.provider.certpath.ConstraintsChecker;

public class ConstraintsCheckerWitupTest {

    public static void main(String[] args) {
        testInitBackward();
        testInitForwardThrows();
    }

    static void testInitBackward() {
        ConstraintsChecker checker = new ConstraintsChecker(3);
        try {
            checker.init(false);
        } catch (CertPathValidatorException e) {
            throw new RuntimeException("Unexpected exception in backward init", e);
        }
    }

    static void testInitForwardThrows() {
        ConstraintsChecker checker = new ConstraintsChecker(3);
        boolean exceptionThrown = false;
        try {
            checker.init(true);
        } catch (CertPathValidatorException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new RuntimeException("Expected CertPathValidatorException not thrown on forward init");
        }
    }
}

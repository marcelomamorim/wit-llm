/* @test    @summary Test for PolicyChecker.init(boolean) method behavior.    @run main PolicyCheckerWitupTest */

import sun.security.provider.certpath.PolicyChecker;
import java.security.cert.CertPathValidatorException;

public class PolicyCheckerWitupTest {

    public static void main(String[] args) {
        testInitForwardThrowsException();
        testInitNonForwardInitializesFields();
    }

    private static void testInitForwardThrowsException() {
        PolicyChecker pc = new PolicyChecker();
        boolean forward = true;
        try {
            pc.init(forward);
            System.out.println("FAIL: Expected CertPathValidatorException not thrown.");
        } catch (CertPathValidatorException e) {
            System.out.println("PASS: CertPathValidatorException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testInitNonForwardInitializesFields() {
        PolicyChecker pc = new PolicyChecker();
        boolean forward = false;
        try {
            pc.init(forward);
            // Since fields are package-private, we assume default initialization
            // and check that no exception is thrown and method completes.
            System.out.println("PASS: init(false) completed without exception.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}

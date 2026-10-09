/* @test    @summary Test for setBasicConstraints with valid and invalid inputs    @run main X509CertSelectorWitupTest_Ped4390ffa1cd */

import java.security.cert.X509CertSelector;

public class X509CertSelectorWitupTest_Ped4390ffa1cd {

    public static void main(String[] args) {
        testSetBasicConstraintsValid();
        testSetBasicConstraintsInvalid();
    }

    private static void testSetBasicConstraintsValid() {
        X509CertSelector selector = new X509CertSelector();
        try {
            selector.setBasicConstraints(0);
            selector.setBasicConstraints(10);
            selector.setBasicConstraints(-1);
            selector.setBasicConstraints(2);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs", e);
        }
    }

    private static void testSetBasicConstraintsInvalid() {
        X509CertSelector selector = new X509CertSelector();
        try {
            selector.setBasicConstraints(-3);
            throw new AssertionError("Expected IllegalArgumentException for input -3");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        try {
            selector.setBasicConstraints(-100);
            // No exception expected for values less than -2, as per current implementation
            // But if the implementation changes, this test may need adjustment
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for input -100", e);
        }
    }
}

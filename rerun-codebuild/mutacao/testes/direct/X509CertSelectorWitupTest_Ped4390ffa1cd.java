/* @test    @summary Test setting basic constraints in X509CertSelector    @run main X509CertSelectorWitupTest_Ped4390ffa1cd */

import java.security.cert.X509CertSelector;

public class X509CertSelectorWitupTest_Ped4390ffa1cd {

    public static void main(String[] args) {
        testSetBasicConstraintsValid();
        testSetBasicConstraintsInvalid();
    }

    private static void testSetBasicConstraintsValid() {
        X509CertSelector selector = new X509CertSelector();
        int validValue = 0;
        selector.setBasicConstraints(validValue);
        System.out.println("Set valid basic constraints: " + validValue);
    }

    private static void testSetBasicConstraintsInvalid() {
        X509CertSelector selector = new X509CertSelector();
        int invalidValue = -3;
        boolean exceptionThrown = false;
        try {
            selector.setBasicConstraints(invalidValue);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            System.out.println("Caught expected exception for invalid value: " + invalidValue);
        }
        if (!exceptionThrown) {
            System.err.println("Failed to throw exception for invalid value: " + invalidValue);
        }
    }
}

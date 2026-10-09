/* @test    @summary Test setting basic constraints on X509CertSelector    @run main X509CertSelectorWitupTest_Pf33f879e4918 */

import java.security.cert.X509CertSelector;

public class X509CertSelectorWitupTest_Pf33f879e4918 {
    public static void main(String[] args) {
        X509CertSelector selector = new X509CertSelector();

        // Test setting basic constraints to a valid value
        selector.setBasicConstraints(0);
        System.out.println("Set basic constraints to 0: " + selector);

        // Test setting basic constraints to -2 (minimum valid value)
        selector.setBasicConstraints(-2);
        System.out.println("Set basic constraints to -2: " + selector);

        // Test setting basic constraints to an invalid value (-3), expecting exception
        boolean exceptionThrown = false;
        try {
            selector.setBasicConstraints(-3);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            System.out.println("Caught expected exception for -3: " + e.getMessage());
        }
        if (!exceptionThrown) {
            System.err.println("Failed to throw exception for invalid input -3");
        }

        // Test setting basic constraints to a large positive value
        selector.setBasicConstraints(10);
        System.out.println("Set basic constraints to 10: " + selector);
    }
}

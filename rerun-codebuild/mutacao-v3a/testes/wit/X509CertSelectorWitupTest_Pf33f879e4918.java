/* @test    @summary Tests for setBasicConstraints to verify handling of invalid constraints  @run main X509CertSelectorWitupTest_Pf33f879e4918 */

import java.security.cert.X509CertSelector;

public class X509CertSelectorWitupTest_Pf33f879e4918 {
    public static void main(String[] args) {
        testSetBasicConstraintsNegative();
    }

    private static void testSetBasicConstraintsNegative() {
        X509CertSelector selector = new X509CertSelector();
        try {
            selector.setBasicConstraints(-3);
            System.out.println("Failed: Expected IllegalArgumentException for value -3");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for value -3");
        }
        // Test edge case at -2, should not throw
        try {
            selector.setBasicConstraints(-2);
            System.out.println("Passed: No exception for value -2");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed: Unexpected exception for value -2");
        }
        // Test valid positive value, should not throw
        try {
            selector.setBasicConstraints(0);
            System.out.println("Passed: No exception for value 0");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed: Unexpected exception for value 0");
        }
    }
}

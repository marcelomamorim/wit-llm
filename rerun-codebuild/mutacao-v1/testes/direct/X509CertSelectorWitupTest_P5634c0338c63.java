/* @test    @summary Test setBasicConstraints method of X509CertSelector for valid and invalid inputs    @run main X509CertSelectorWitupTest_P5634c0338c63 */

import java.security.cert.X509CertSelector;

public class X509CertSelectorWitupTest_P5634c0338c63 {

    public static void main(String[] args) {
        testSetBasicConstraintsValid();
        testSetBasicConstraintsInvalid();
    }

    private static void testSetBasicConstraintsValid() {
        X509CertSelector selector = new X509CertSelector();
        try {
            selector.setBasicConstraints(0);
            selector.setBasicConstraints(5);
            selector.setBasicConstraints(10);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input: " + e);
        }
    }

    private static void testSetBasicConstraintsInvalid() {
        X509CertSelector selector = new X509CertSelector();
        try {
            selector.setBasicConstraints(-3);
            throw new AssertionError("Expected IllegalArgumentException for input -3")
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
        try {
            selector.setBasicConstraints(-5);
            throw new AssertionError("Expected IllegalArgumentException for input -5")
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}

/* @test    @summary Test for setBasicConstraints method in X509CertSelector to verify exception handling for invalid input    @run main X509CertSelectorWitupTest_P5634c0338c63 */

import java.security.cert.X509CertSelector;

public class X509CertSelectorWitupTest_P5634c0338c63 {

    public static void main(String[] args) {
        testSetBasicConstraintsWithInvalidValue();
        testSetBasicConstraintsWithValidValue();
    }

    private static void testSetBasicConstraintsWithInvalidValue() {
        X509CertSelector selector = new X509CertSelector();
        try {
            selector.setBasicConstraints(-3);
            throw new AssertionError("Expected IllegalArgumentException for value -3");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testSetBasicConstraintsWithValidValue() {
        X509CertSelector selector = new X509CertSelector();
        try {
            selector.setBasicConstraints(0);
            // No exception expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid value 0: " + e);
        }
        try {
            selector.setBasicConstraints(2);
            // No exception expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid value 2: " + e);
        }
        try {
            selector.setBasicConstraints(-2);
            // No exception expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid value -2: " + e);
        }
    }
}

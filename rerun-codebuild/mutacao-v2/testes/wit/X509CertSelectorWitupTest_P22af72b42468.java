/*
 * @test
 * @summary Test X509CertSelector.setBasicConstraints(int) for valid and invalid inputs
 * @run main X509CertSelectorWitupTest_P22af72b42468
 */

import java.security.cert.X509CertSelector;

public class X509CertSelectorWitupTest_P22af72b42468 {
    public static void main(String[] args) {
        X509CertSelector selector = new X509CertSelector();

        // Test valid values including boundary values
        selector.setBasicConstraints(-2);
        selector.setBasicConstraints(-1);
        selector.setBasicConstraints(0);
        selector.setBasicConstraints(10);

        // Test that IllegalArgumentException is thrown for values less than -2
        try {
            selector.setBasicConstraints(-3);
            throw new RuntimeException("Expected IllegalArgumentException for input -3");
        } catch (IllegalArgumentException expected) {
        }

        try {
            selector.setBasicConstraints(Integer.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for Integer.MIN_VALUE");
        } catch (IllegalArgumentException expected) {
        }
    }
}

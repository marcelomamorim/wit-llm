/*
 * @test
 * @summary Test X509CertSelector.setBasicConstraints with valid and invalid inputs
 * @run main X509CertSelectorWitupTest
 */

import java.security.cert.X509CertSelector;

public class X509CertSelectorWitupTest {
    public static void main(String[] args) {
        X509CertSelector selector = new X509CertSelector();

        // Test valid values
        selector.setBasicConstraints(-2);
        selector.setBasicConstraints(-1);
        selector.setBasicConstraints(0);
        selector.setBasicConstraints(10);

        // Test invalid value: less than -2 should throw IllegalArgumentException
        try {
            selector.setBasicConstraints(-3);
            throw new RuntimeException("Expected IllegalArgumentException for value -3");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            selector.setBasicConstraints(Integer.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for Integer.MIN_VALUE");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}

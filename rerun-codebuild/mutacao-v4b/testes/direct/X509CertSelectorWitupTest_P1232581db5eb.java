/* @test
   @summary Verifies the behavior of setBasicConstraints method in X509CertSelector.
   @run main X509CertSelectorWitupTest_P1232581db5eb
 */
import java.security.cert.X509CertSelector;
public class X509CertSelectorWitupTest_P1232581db5eb {
    public static void main(String[] args) {
        X509CertSelector selector = new X509CertSelector();
        // Test setting a valid basic constraints value
        selector.setBasicConstraints(0);
        // Since there's no public getter, test that setting a negative value less than -2 throws IllegalArgumentException
        boolean exceptionThrown = false;
        try {
            selector.setBasicConstraints(-3);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for value less than -2");
        }
        // Test setting the minimum valid value (-2)
        selector.setBasicConstraints(-2);
        // Test setting a typical positive value
        selector.setBasicConstraints(5);
        // Test setting the maximum value (Integer.MAX_VALUE)
        selector.setBasicConstraints(Integer.MAX_VALUE);
    }
}

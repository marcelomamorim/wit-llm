/* @test
   @summary Testa o método setBasicConstraints com valores válidos e inválidos.
   @run main X509CertSelectorWitupTest_P1232581db5eb
 */
import java.security.cert.X509CertSelector;
public class X509CertSelectorWitupTest_P1232581db5eb {
    public static void main(String[] args) {
        testSetBasicConstraintsValid();
        testSetBasicConstraintsInvalid();
    }

    private static void testSetBasicConstraintsValid() {
        X509CertSelector selector = new X509CertSelector();
        // Testando valores válidos: -2, 0, 1, 10
        int[] validValues = {-2, 0, 1, 10};
        for (int val : validValues) {
            try {
                selector.setBasicConstraints(val);
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception for value: " + val, e);
            }
        }
    }

    private static void testSetBasicConstraintsInvalid() {
        X509CertSelector selector = new X509CertSelector();
        // Testando valor inválido: -3 (menor que -2)
        int invalidValue = -3;
        try {
            selector.setBasicConstraints(invalidValue);
            throw new AssertionError("Expected IllegalArgumentException for value: " + invalidValue);
        } catch (IllegalArgumentException e) {
            // esperado
        }
        // Testando valor inválido: Integer.MIN_VALUE
        int minValue = Integer.MIN_VALUE;
        try {
            selector.setBasicConstraints(minValue);
            throw new AssertionError("Expected IllegalArgumentException for value: " + minValue);
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }
}

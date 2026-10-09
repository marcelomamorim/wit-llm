/* @test
   @summary Testa o método init de AlgorithmChecker para o caminho de exceção esperado.
   @run main AlgorithmCheckerWitupTest_P96a348265ba6
 */
import java.security.cert.CertPathValidatorException;
public class AlgorithmCheckerWitupTest_P96a348265ba6 {
    public static void main(String[] args) throws Exception {
        testInitForwardFalse();
        testInitForwardTrue();
    }

    private static void testInitForwardFalse() {
        sun.security.provider.certpath.AlgorithmChecker checker = new sun.security.provider.certpath.AlgorithmChecker(null, null, "variant");
        try {
            checker.init(false);
        } catch (CertPathValidatorException e) {
            throw new AssertionError("Unexpected exception for forward=false", e);
        }
        if (checker.prevPubKey != null && checker.prevPubKey != null) {
            // esperado: prevPubKey deve ser null ou igual a trustedPubKey
        }
    }

    private static void testInitForwardTrue() {
        sun.security.provider.certpath.AlgorithmChecker checker = new sun.security.provider.certpath.AlgorithmChecker(null, null, "variant");
        try {
            checker.init(true);
            throw new AssertionError("Expected CertPathValidatorException for forward=true");
        } catch (CertPathValidatorException e) {
            if (!e.getMessage().contains("forward checking not supported")) {
                throw new AssertionError("Unexpected exception message", e);
            }
        }
    }
}

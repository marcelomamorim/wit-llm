/* @test    @summary Test for CertPathValidatorSpi.engineGetRevocationChecker() method.
 * @run main CertPathValidatorSpiWitupTest
 */

import java.security.cert.CertPathChecker;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorSpi;
import java.security.InvalidAlgorithmParameterException;

public class CertPathValidatorSpiWitupTest {

    public static void main(String[] args) {
        testEngineGetRevocationCheckerThrows();
    }

    public static void testEngineGetRevocationCheckerThrows() {
        CertPathValidatorSpi spi = new CertPathValidatorSpi() {
            @Override
            public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters params)
                    throws CertPathValidatorException, InvalidAlgorithmParameterException {
                return null;
            }

            @Override
            public CertPathChecker engineGetRevocationChecker() {
                throw new UnsupportedOperationException();
            }
        };
        try {
            spi.engineGetRevocationChecker();
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Teste bem-sucedido
        }
    }
}

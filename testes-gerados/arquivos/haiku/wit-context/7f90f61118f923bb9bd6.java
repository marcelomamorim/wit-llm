/*
 * @test
 * @summary Checks that the default CertPathValidatorSpi.engineGetRevocationChecker() throws UnsupportedOperationException
 * @run main CertPathValidatorSpiWitupTest
 */
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathChecker;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;

public class CertPathValidatorSpiWitupTest {

    static class TestSpi extends CertPathValidatorSpi {
        @Override
        public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters params)
                throws CertPathValidatorException, InvalidAlgorithmParameterException {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        CertPathValidatorSpi spi = new TestSpi();
        try {
            CertPathChecker checker = spi.engineGetRevocationChecker();
            throw new RuntimeException("Expected UnsupportedOperationException, got " + checker);
        } catch (UnsupportedOperationException expected) {
            // expected default behavior
        }
    }
}

/* @test    @summary Test engineGetRevocationChecker method of CertPathValidatorSpi    @run main CertPathValidatorSpiWitupTest */

import java.security.cert.CertPathValidatorSpi;
import java.security.cert.CertPathChecker;

public class CertPathValidatorSpiWitupTest {

    public static void main(String[] args) {
        CertPathValidatorSpi spi = new CertPathValidatorSpi() {
            @Override
            public CertPathValidatorResult engineValidate(java.security.cert.CertPath certPath, java.security.cert.CertPathParameters params) throws java.security.cert.CertPathValidatorException, java.security.InvalidAlgorithmParameterException {
                return null;
            }
        };
        CertPathChecker checker = spi.engineGetRevocationChecker();
        if (checker != null) {
            throw new AssertionError("Expected null, got: " + checker);
        }
    }
}

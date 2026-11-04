/*
 * @test
 * @summary Test CertPathValidatorSpi.engineGetRevocationChecker throws UnsupportedOperationException
 * @run main CertPathValidatorSpiWitupTest
 */

import java.security.cert.CertPathChecker;
import java.security.cert.CertPathValidatorSpi;

public class CertPathValidatorSpiWitupTest {
    public static void main(String[] args) {
        CertPathValidatorSpi spi = new CertPathValidatorSpi() {
            @Override
            public java.security.cert.CertPathValidatorResult engineValidate(
                    java.security.cert.CertPath certPath,
                    java.security.cert.CertPathParameters params)
                    throws java.security.cert.CertPathValidatorException, java.security.InvalidAlgorithmParameterException {
                // Minimal implementation to allow instantiation
                throw new java.security.cert.CertPathValidatorException("Not implemented");
            }
        };

        boolean thrown = false;
        try {
            CertPathChecker checker = spi.engineGetRevocationChecker();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
        }
    }
}

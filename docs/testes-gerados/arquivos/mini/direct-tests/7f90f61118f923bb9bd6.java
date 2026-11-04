/*
 * @test
 * @summary Test engineGetRevocationChecker throws UnsupportedOperationException
 * @run main CertPathValidatorSpiWitupTest
 */

import java.security.cert.CertPathChecker;
import java.security.cert.CertPathValidatorSpi;

public class CertPathValidatorSpiWitupTest {

    static class DummyCertPathValidatorSpi extends CertPathValidatorSpi {
        @Override
        public java.security.cert.CertPathValidatorResult engineValidate(java.security.cert.CertPath certPath, java.security.cert.CertPathParameters params) {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        CertPathValidatorSpi spi = new DummyCertPathValidatorSpi();
        try {
            spi.engineGetRevocationChecker();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}

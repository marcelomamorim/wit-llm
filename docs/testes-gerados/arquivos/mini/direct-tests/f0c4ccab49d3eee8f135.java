/*
 * @test
 * @summary Test engineGetRevocationChecker throws UnsupportedOperationException
 * @run main CertPathBuilderSpiWitupTest
 */

import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathChecker;

public class CertPathBuilderSpiWitupTest {

    static class TestCertPathBuilderSpi extends CertPathBuilderSpi {
        @Override
        public java.security.cert.CertPathBuilderResult engineBuild(java.security.cert.CertPathParameters params) {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        CertPathBuilderSpi spi = new TestCertPathBuilderSpi();
        try {
            spi.engineGetRevocationChecker();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}

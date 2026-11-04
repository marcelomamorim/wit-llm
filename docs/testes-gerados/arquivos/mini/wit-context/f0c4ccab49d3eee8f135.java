/*
 * @test
 * @summary Test CertPathBuilderSpi.engineGetRevocationChecker throws UnsupportedOperationException
 * @run main CertPathBuilderSpiWitupTest
 */

import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathChecker;

public class CertPathBuilderSpiWitupTest {

    public static void main(String[] args) {
        CertPathBuilderSpi spi = new CertPathBuilderSpi() {
            @Override
            public java.security.cert.CertPathBuilderResult engineBuild(java.security.cert.CertPathParameters params) {
                throw new UnsupportedOperationException("Not implemented for this test");
            }
        };

        try {
            CertPathChecker checker = spi.engineGetRevocationChecker();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown, got: " + checker);
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}

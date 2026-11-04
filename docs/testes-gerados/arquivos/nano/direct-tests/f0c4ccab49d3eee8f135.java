/* @test    @summary Test engineGetRevocationChecker method of CertPathBuilderSpi.    @run main CertPathBuilderSpiWitupTest */

import java.security.cert.CertPathChecker;
import java.security.cert.CertPathBuilderSpi;

public class CertPathBuilderSpiWitupTest {

    public static void main(String[] args) {
        CertPathBuilderSpi spi = new CertPathBuilderSpi() {
            @Override
            public CertPathBuilderResult engineBuild(CertPathParameters params) {
                return null;
            }
        };
        CertPathChecker checker = spi.engineGetRevocationChecker();
        if (checker != null) {
            throw new AssertionError("Expected null, but got: " + checker);
        }
        System.out.println("Test passed: engineGetRevocationChecker returns null as expected.");
    }
}

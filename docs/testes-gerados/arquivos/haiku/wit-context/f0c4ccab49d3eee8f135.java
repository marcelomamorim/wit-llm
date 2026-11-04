/*
 * @test
 * @summary Verifies that the default CertPathBuilderSpi.engineGetRevocationChecker() throws UnsupportedOperationException
 * @run main CertPathBuilderSpiWitupTest
 */

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathChecker;
import java.security.cert.CertPathParameters;

public class CertPathBuilderSpiWitupTest {

    static class EmptySpi extends CertPathBuilderSpi {
        @Override
        public CertPathBuilderResult engineBuild(CertPathParameters params)
                throws CertPathBuilderException, InvalidAlgorithmParameterException {
            throw new UnsupportedOperationException("engineBuild not used by this test");
        }
    }

    static void checkThrowsUnsupported(CertPathBuilderSpi spi) {
        boolean thrown = false;
        try {
            CertPathChecker checker = spi.engineGetRevocationChecker();
            System.out.println("Unexpected return: " + checker);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from engineGetRevocationChecker()");
        }
    }

    public static void main(String[] args) throws Exception {
        CertPathBuilderSpi spi = new EmptySpi();
        checkThrowsUnsupported(spi);
        checkThrowsUnsupported(spi);
        CertPathBuilderSpi another = new EmptySpi();
        checkThrowsUnsupported(another);
    }
}

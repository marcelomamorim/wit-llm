/*
 * @test
 * @summary Verify that the default CertPathBuilderSpi.engineGetRevocationChecker throws UnsupportedOperationException and that subclass overrides are honored
 * @run main CertPathBuilderSpiWitupTest
 */

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathChecker;
import java.security.cert.CertPathParameters;

public class CertPathBuilderSpiWitupTest {

    static class DefaultSpi extends CertPathBuilderSpi {
        @Override
        public CertPathBuilderResult engineBuild(CertPathParameters params)
                throws CertPathBuilderException, InvalidAlgorithmParameterException {
            return null;
        }
    }

    static class OverridingSpi extends DefaultSpi {
        @Override
        public CertPathChecker engineGetRevocationChecker() {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        CertPathBuilderSpi spi = new DefaultSpi();
        boolean thrown = false;
        try {
            spi.engineGetRevocationChecker();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException from default engineGetRevocationChecker");
        }

        CertPathBuilderSpi overriding = new OverridingSpi();
        CertPathChecker checker = overriding.engineGetRevocationChecker();
        if (checker != null) {
            throw new AssertionError("overridden engineGetRevocationChecker should return null in this test");
        }
    }
}

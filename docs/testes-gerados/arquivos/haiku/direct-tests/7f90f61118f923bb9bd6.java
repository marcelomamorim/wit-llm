/*
 * @test
 * @summary Verifies the default engineGetRevocationChecker behavior of CertPathValidatorSpi and that subclasses can override it
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

    static class Plain extends CertPathValidatorSpi {
        @Override
        public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters params)
                throws CertPathValidatorException, InvalidAlgorithmParameterException {
            return null;
        }
    }

    static class NullChecker extends CertPathValidatorSpi {
        @Override
        public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters params)
                throws CertPathValidatorException, InvalidAlgorithmParameterException {
            return null;
        }

        @Override
        public CertPathChecker engineGetRevocationChecker() {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        Plain plain = new Plain();
        boolean thrown = false;
        try {
            plain.engineGetRevocationChecker();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from default engineGetRevocationChecker");
        }

        NullChecker overriding = new NullChecker();
        CertPathChecker checker = overriding.engineGetRevocationChecker();
        if (checker != null) {
            throw new RuntimeException("Overridden engineGetRevocationChecker should return null");
        }
    }
}

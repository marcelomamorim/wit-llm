/*
 * @test
 * @summary CertificateFactorySpi.engineGetCertPathEncodings throws UnsupportedOperationException by default
 * @run main CertificateFactorySpiWitupTest
 */

import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.util.Collection;
import java.util.Collections;

public class CertificateFactorySpiWitupTest {

    static class StubSpi extends CertificateFactorySpi {
        @Override
        public Certificate engineGenerateCertificate(InputStream inStream) throws CertificateException {
            return null;
        }

        @Override
        public Collection<? extends Certificate> engineGenerateCertificates(InputStream inStream) throws CertificateException {
            return Collections.emptyList();
        }

        @Override
        public CRL engineGenerateCRL(InputStream inStream) throws CRLException {
            return null;
        }

        @Override
        public Collection<? extends CRL> engineGenerateCRLs(InputStream inStream) throws CRLException {
            return Collections.emptyList();
        }
    }

    public static void main(String[] args) {
        StubSpi spi = new StubSpi();

        boolean thrown = false;
        try {
            spi.engineGetCertPathEncodings();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("engineGetCertPathEncodings should throw UnsupportedOperationException");
        }

        thrown = false;
        try {
            spi.engineGetCertPathEncodings();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Second call should also throw UnsupportedOperationException");
        }
    }
}

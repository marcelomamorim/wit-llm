/*
 * @test
 * @summary Verify that the default CertificateFactorySpi.engineGetCertPathEncodings throws UnsupportedOperationException
 * @run main CertificateFactorySpiWitupTest
 */

import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class CertificateFactorySpiWitupTest {

    static class StubFactory extends CertificateFactorySpi {
        public Certificate engineGenerateCertificate(InputStream inStream) throws CertificateException {
            return null;
        }

        public Collection<? extends Certificate> engineGenerateCertificates(InputStream inStream) throws CertificateException {
            return Collections.emptyList();
        }

        public CRL engineGenerateCRL(InputStream inStream) throws CRLException {
            return null;
        }

        public Collection<? extends CRL> engineGenerateCRLs(InputStream inStream) throws CRLException {
            return Collections.emptyList();
        }
    }

    public static void main(String[] args) {
        CertificateFactorySpi spi = new StubFactory();
        try {
            Iterator<String> it = spi.engineGetCertPathEncodings();
            throw new RuntimeException("Expected UnsupportedOperationException but got " + it);
        } catch (UnsupportedOperationException expected) {
            // expected behavior of the default implementation
        }
    }
}

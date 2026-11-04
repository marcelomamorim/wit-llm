/*
 * @test
 * @summary Test engineGetCertPathEncodings throws UnsupportedOperationException
 * @run main CertificateFactorySpiWitupTest
 */

import java.security.cert.CertificateFactorySpi;
import java.util.Iterator;

public class CertificateFactorySpiWitupTest {

    static class DummyCertificateFactorySpi extends CertificateFactorySpi {
        @Override
        public Iterator<String> engineGetCertPathEncodings() {
            return super.engineGetCertPathEncodings();
        }
    }

    public static void main(String[] args) {
        CertificateFactorySpi spi = new DummyCertificateFactorySpi();
        try {
            spi.engineGetCertPathEncodings();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}

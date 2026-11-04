/*
 * @test
 * @summary Test engineGetCertPathEncodings throws UnsupportedOperationException
 * @run main CertificateFactorySpiWitupTest
 */

import java.security.cert.CertificateFactorySpi;
import java.util.Iterator;

public class CertificateFactorySpiWitupTest {
    public static void main(String[] args) {
        CertificateFactorySpi spi = new CertificateFactorySpi() {
            // No override, use default implementation
        };
        try {
            spi.engineGetCertPathEncodings();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}

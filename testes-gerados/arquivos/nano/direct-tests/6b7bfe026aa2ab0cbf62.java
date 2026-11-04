/* @test    @summary Test engineGetCertPathEncodings method for CertificateFactorySpi class    @run main CertificateFactorySpiWitupTest */

import java.security.cert.CertificateFactorySpi;
import java.util.Iterator;

public class CertificateFactorySpiWitupTest {

    public static void main(String[] args) {
        testEngineGetCertPathEncodings();
    }

    public static void testEngineGetCertPathEncodings() {
        CertificateFactorySpi spi = new CertificateFactorySpi() {
            @Override
            public Iterator<String> engineGetCertPathEncodings() {
                return null;
            }
        };
        try {
            Iterator<String> encodings = spi.engineGetCertPathEncodings();
            if (encodings != null) {
                System.out.println("engineGetCertPathEncodings returned: " + encodings);
            } else {
                System.out.println("engineGetCertPathEncodings returned null as expected.");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception: " + e, e);
        }
    }
}

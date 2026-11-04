/* @test    @summary Test for engineGetCertPathEncodings method in CertificateFactorySpi.    @run main CertificateFactorySpiWitupTest */

import java.security.cert.CertificateFactorySpi;
import java.util.Iterator;

public class CertificateFactorySpiWitupTest {

    public static void main(String[] args) {
        testEngineGetCertPathEncodingsThrowsException();
    }

    private static void testEngineGetCertPathEncodingsThrowsException() {
        CertificateFactorySpi spi = new CertificateFactorySpi() {
            @Override
            public Iterator<String> engineGetCertPathEncodings() {
                throw new UnsupportedOperationException();
            }
        };
        try {
            spi.engineGetCertPathEncodings();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException was correctly thrown.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}

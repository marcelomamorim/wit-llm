/*
 * @test
 * @summary Test sun.security.x509.X509CertImpl.getEncoded() returns a clone of the internal encoding
 * @run main sun.security.x509.X509CertImplWitupTest
 */

import sun.security.x509.X509CertImpl;
import java.security.cert.CertificateEncodingException;

public class X509CertImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create an empty X509CertImpl instance
        X509CertImpl cert = new X509CertImpl();

        // Call getEncoded() twice and verify that the returned arrays are equal but not the same instance
        byte[] encoded1 = cert.getEncoded();
        byte[] encoded2 = cert.getEncoded();

        if (encoded1 == null) {
            throw new RuntimeException("getEncoded() returned null");
        }

        if (encoded1.length != encoded2.length) {
            throw new RuntimeException("getEncoded() returned arrays of different length");
        }

        for (int i = 0; i < encoded1.length; i++) {
            if (encoded1[i] != encoded2[i]) {
                throw new RuntimeException("getEncoded() returned arrays with different content");
            }
        }

        if (encoded1 == encoded2) {
            throw new RuntimeException("getEncoded() did not return a clone, same array instance returned");
        }
    }
}

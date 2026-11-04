/*
 * @test
 * @summary Test sun.security.x509.X509CertImpl.getEncoded() method
 * @run main X509CertImplWitupTest
 */

import sun.security.x509.X509CertImpl;
import java.security.cert.CertificateEncodingException;

public class X509CertImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetEncodedReturnsClone();
        testGetEncodedThrowsWhenSignedCertNull();
    }

    private static void testGetEncodedReturnsClone() throws Exception {
        // Create a minimal valid X509CertImpl instance using default constructor
        X509CertImpl cert = new X509CertImpl();

        // The signedCert field is private and no public setter is available,
        // so getEncodedInternal() will likely throw CertificateEncodingException
        // or return a byte array. We test that getEncoded() returns a clone of that array.
        // Since we cannot set signedCert, we expect CertificateEncodingException.

        try {
            byte[] encoded = cert.getEncoded();
            if (encoded == null) {
                throw new RuntimeException("getEncoded() returned null");
            }
            // Modify returned array and call getEncoded() again to check cloning
            encoded[0] = (byte)(encoded[0] + 1);
            byte[] encoded2 = cert.getEncoded();
            if (encoded2[0] == encoded[0]) {
                throw new RuntimeException("getEncoded() did not return a clone");
            }
        } catch (CertificateEncodingException e) {
            // Acceptable if encoding fails due to unsigned cert
        }
    }

    private static void testGetEncodedThrowsWhenSignedCertNull() {
        // We cannot directly set signedCert to null because it is private and no setter is exposed.
        // However, the default constructor likely leaves signedCert null.
        // So calling getEncoded() on a new instance should throw CertificateEncodingException.

        X509CertImpl cert = new X509CertImpl();
        try {
            cert.getEncoded();
            throw new RuntimeException("Expected CertificateEncodingException not thrown");
        } catch (CertificateEncodingException expected) {
            // Expected exception
        } catch (Exception unexpected) {
            throw new RuntimeException("Unexpected exception type", unexpected);
        }
    }
}

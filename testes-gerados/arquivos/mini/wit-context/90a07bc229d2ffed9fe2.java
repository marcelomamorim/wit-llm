/*
 * @test
 * @summary Test sun.security.x509.X509CertImpl.getEncodedInternal() behavior
 * @run main X509CertImplWitupTest
 */

import sun.security.x509.X509CertImpl;
import java.security.cert.CertificateEncodingException;

public class X509CertImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetEncodedInternalThrowsWhenSignedCertNull();
        testGetEncodedInternalReturnsSignedCert();
    }

    private static void testGetEncodedInternalThrowsWhenSignedCertNull() {
        X509CertImpl cert = new X509CertImpl();
        // signedCert is private and not set by default constructor, so it is null
        try {
            cert.getEncodedInternal();
            throw new RuntimeException("Expected CertificateEncodingException not thrown");
        } catch (CertificateEncodingException e) {
            // expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Null certificate to encode")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testGetEncodedInternalReturnsSignedCert() throws Exception {
        // Create a dummy encoded certificate byte array
        byte[] dummyEncoded = new byte[] {0x30, 0x03, 0x02, 0x01, 0x01};
        // Use constructor that sets signedCert to dummyEncoded
        X509CertImpl cert = new X509CertImpl(dummyEncoded);
        byte[] encoded = cert.getEncodedInternal();
        if (encoded == null) {
            throw new RuntimeException("getEncodedInternal() returned null");
        }
        if (encoded.length != dummyEncoded.length) {
            throw new RuntimeException("Returned encoded length mismatch");
        }
        for (int i = 0; i < encoded.length; i++) {
            if (encoded[i] != dummyEncoded[i]) {
                throw new RuntimeException("Returned encoded content mismatch at index " + i);
            }
        }
    }
}

/*
 * @test
 * @summary Test sun.security.x509.X509CertImpl.getEncodedInternal() behavior
 * @run main X509CertImplWitupTest
 */

import sun.security.x509.X509CertImpl;
import java.security.cert.CertificateEncodingException;

public class X509CertImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetEncodedInternalThrows();
        testGetEncodedInternalReturns();
    }

    private static void testGetEncodedInternalThrows() {
        X509CertImpl cert = new X509CertImpl();
        try {
            cert.getEncodedInternal();
            throw new RuntimeException("Expected CertificateEncodingException not thrown");
        } catch (CertificateEncodingException e) {
            if (!"Null certificate to encode".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testGetEncodedInternalReturns() throws Exception {
        byte[] dummyEncoded = new byte[] {0x30, 0x31, 0x32};
        X509CertImpl cert = new X509CertImpl();

        // Use reflection to set the private field 'signedCert' since no public setter is available
        java.lang.reflect.Field f = X509CertImpl.class.getDeclaredField("signedCert");
        f.setAccessible(true);
        f.set(cert, dummyEncoded);

        byte[] result = cert.getEncodedInternal();
        if (result == null) {
            throw new RuntimeException("getEncodedInternal() returned null");
        }
        if (result.length != dummyEncoded.length) {
            throw new RuntimeException("Returned byte array length mismatch");
        }
        for (int i = 0; i < result.length; i++) {
            if (result[i] != dummyEncoded[i]) {
                throw new RuntimeException("Returned byte array content mismatch at index " + i);
            }
        }
    }
}

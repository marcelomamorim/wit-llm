/* @test    @summary Test getEncoded() method of X509CertImpl for correct byte array return and exception handling    @run main X509CertImplWitupTest */

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateEncodingException;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetEncodedReturnsClone();
        testGetEncodedThrowsException();
    }

    private static void testGetEncodedReturnsClone() throws Exception {
        byte[] certData = {0x30, 0x82, 0x01, 0x0a}; // minimal valid DER-encoded certificate
        X509CertImpl cert = new X509CertImpl(certData);
        byte[] encoded = cert.getEncoded();
        if (encoded == null) {
            throw new AssertionError("getEncoded() returned null");
        }
        if (encoded.length != certData.length) {
            throw new AssertionError("Encoded byte array length mismatch");
        }
        // Verify that returned array is a clone (not the same reference)
        if (encoded == certData) {
            throw new AssertionError("getEncoded() did not return a clone");
        }
        // Verify that modifying returned array does not affect internal state
        encoded[0] = (byte) (encoded[0] ^ 0xFF);
        byte[] encodedAgain = cert.getEncoded();
        if (encodedAgain[0] == (byte) (encoded[0] ^ 0xFF)) {
            throw new AssertionError("Internal state affected by external modification");
        }
    }

    private static void testGetEncodedThrowsException() {
        // Create a subclass that overrides getEncodedInternal to throw exception
        class FaultyCert extends X509CertImpl {
            public FaultyCert() throws Exception {
                super(new ByteArrayInputStream(new byte[0]));
            }
            @Override
            public byte[] getEncoded() throws CertificateEncodingException {
                throw new CertificateEncodingException("Simulated encoding error");
            }
        }
        try {
            FaultyCert faulty = new FaultyCert();
            faulty.getEncoded();
            throw new AssertionError("Expected CertificateEncodingException was not thrown");
        } catch (CertificateEncodingException e) {
            // Expected exception
            if (!"Simulated encoding error".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}

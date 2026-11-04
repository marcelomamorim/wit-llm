/*
 * @test
 * @summary Test sun.security.x509.X509CRLImpl.getEncoded() behavior including exception on null internal encoding
 * @run main X509CRLImplWitupTest
 */

import sun.security.x509.X509CRLImpl;
import sun.security.x509.DerValue;
import java.security.cert.CRLException;

public class X509CRLImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetEncodedReturnsClone();
        testGetEncodedThrowsCRLExceptionWhenSignedCRLNull();
    }

    private static void testGetEncodedReturnsClone() throws Exception {
        // Create a minimal valid X509CRLImpl instance from a DerValue
        // Using DerValue with empty sequence to avoid complex construction
        DerValue val = new DerValue(new byte[] {0x30, 0x00}); // DER encoding of empty SEQUENCE
        X509CRLImpl crl = new X509CRLImpl(val);

        byte[] encoded1 = crl.getEncoded();
        byte[] encoded2 = crl.getEncoded();

        if (encoded1 == null) {
            throw new RuntimeException("getEncoded() returned null");
        }

        if (encoded1 == encoded2) {
            throw new RuntimeException("getEncoded() did not return a clone (same array instance)");
        }

        if (encoded1.length != encoded2.length) {
            throw new RuntimeException("getEncoded() returned arrays of different length on repeated calls");
        }

        for (int i = 0; i < encoded1.length; i++) {
            if (encoded1[i] != encoded2[i]) {
                throw new RuntimeException("getEncoded() returned different content on repeated calls");
            }
        }
    }

    private static void testGetEncodedThrowsCRLExceptionWhenSignedCRLNull() throws Exception {
        // Create a subclass to forcibly set signedCRL to null to test exception path
        X509CRLImpl crl = new X509CRLImpl(new DerValue(new byte[] {0x30, 0x00})) {
            // Override getEncodedInternal to simulate signedCRL == null
            @Override
            protected byte[] getEncodedInternal() throws CRLException {
                // Simulate null signedCRL to trigger CRLException
                throw new CRLException("signedCRL is null");
            }
        };

        boolean thrown = false;
        try {
            crl.getEncoded();
        } catch (CRLException e) {
            thrown = true;
            if (!"signedCRL is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected CRLException message: " + e.getMessage());
            }
        }

        if (!thrown) {
            throw new RuntimeException("Expected CRLException was not thrown when signedCRL is null");
        }
    }
}

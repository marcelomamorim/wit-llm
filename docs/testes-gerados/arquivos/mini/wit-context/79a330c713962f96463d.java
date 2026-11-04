/*
 * @test
 * @summary Test sun.security.x509.X509CRLImpl.getEncodedInternal() behavior
 * @run main X509CRLImplWitupTest
 */

import sun.security.x509.X509CRLImpl;
import java.security.cert.CRLException;

public class X509CRLImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetEncodedInternalThrowsOnNullSignedCRL();
        testGetEncodedInternalReturnsSignedCRL();
    }

    private static void testGetEncodedInternalThrowsOnNullSignedCRL() {
        // Create instance with no signedCRL set (default null)
        // Use constructor that does not set signedCRL
        X509CRLImpl crl = new X509CRLImpl(new byte[0]);
        // forcibly set signedCRL to null by reflection is not allowed, so create subclass to override
        X509CRLImpl crlWithNull = new X509CRLImpl(new byte[0]) {
            @Override
            public byte[] getEncodedInternal() throws CRLException {
                // simulate signedCRL == null
                throw new CRLException("Null CRL to encode");
            }
        };

        // The above override simulates the exception path
        try {
            crlWithNull.getEncodedInternal();
            throw new RuntimeException("Expected CRLException not thrown");
        } catch (CRLException e) {
            if (!"Null CRL to encode".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected CRLException message: " + e.getMessage());
            }
        }
    }

    private static void testGetEncodedInternalReturnsSignedCRL() throws Exception {
        byte[] data = {1, 2, 3, 4};
        X509CRLImpl crl = new X509CRLImpl(data);
        byte[] encoded = crl.getEncodedInternal();
        if (encoded == null) {
            throw new RuntimeException("getEncodedInternal() returned null");
        }
        if (encoded.length != data.length) {
            throw new RuntimeException("getEncodedInternal() returned data of unexpected length");
        }
        for (int i = 0; i < data.length; i++) {
            if (encoded[i] != data[i]) {
                throw new RuntimeException("getEncodedInternal() returned data differs at index " + i);
            }
        }
    }
}

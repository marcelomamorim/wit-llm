/*
 * @test
 * @summary Test sun.security.x509.X509CRLImpl.getEncodedInternal() behavior
 * @run main X509CRLImplWitupTest
 */

import sun.security.x509.X509CRLImpl;
import java.security.cert.CRLException;

public class X509CRLImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetEncodedInternalReturnsSignedCRL();
        testGetEncodedInternalThrowsOnNullSignedCRL();
    }

    private static void testGetEncodedInternalReturnsSignedCRL() throws Exception {
        byte[] data = {1, 2, 3, 4, 5};
        X509CRLImpl crl = new X509CRLImpl(data);
        byte[] encoded = crl.getEncodedInternal();
        if (encoded == null) {
            throw new RuntimeException("getEncodedInternal() returned null");
        }
        if (encoded.length != data.length) {
            throw new RuntimeException("getEncodedInternal() returned array of unexpected length");
        }
        for (int i = 0; i < data.length; i++) {
            if (encoded[i] != data[i]) {
                throw new RuntimeException("getEncodedInternal() returned array with unexpected content");
            }
        }
    }

    private static void testGetEncodedInternalThrowsOnNullSignedCRL() {
        X509CRLImpl crl = new X509CRLImpl(new byte[0]);
        try {
            // Use reflection to set signedCRL to null since no public setter
            java.lang.reflect.Field f = X509CRLImpl.class.getDeclaredField("signedCRL");
            f.setAccessible(true);
            f.set(crl, null);

            crl.getEncodedInternal();
            throw new RuntimeException("Expected CRLException not thrown");
        } catch (CRLException e) {
            // Expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}

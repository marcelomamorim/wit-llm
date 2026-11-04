/*
 * @test
 * @summary Test sun.security.x509.X509CRLImpl.getTBSCertList() behavior including exception on uninitialized state
 * @run main sun.security.x509.X509CRLImplWitupTest
 */

import sun.security.x509.X509CRLImpl;
import java.security.cert.CRLException;

public class X509CRLImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetTBSCertListThrowsOnNull();
        testGetTBSCertListReturnsClone();
    }

    private static void testGetTBSCertListThrowsOnNull() {
        // Create instance with null tbsCertList by subclassing to bypass constructor initialization
        X509CRLImpl crl = new X509CRLImpl(new byte[0]) {
            // Override to forcibly set tbsCertList to null
            {
                try {
                    java.lang.reflect.Field f = X509CRLImpl.class.getDeclaredField("tbsCertList");
                    f.setAccessible(true);
                    f.set(this, null);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        try {
            crl.getTBSCertList();
            throw new RuntimeException("Expected CRLException not thrown");
        } catch (CRLException e) {
            if (!"Uninitialized CRL".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testGetTBSCertListReturnsClone() throws Exception {
        // Create instance with non-null tbsCertList
        byte[] crlData = new byte[] {0x30, 0x0}; // minimal valid DER sequence
        X509CRLImpl crl = new X509CRLImpl(crlData);

        byte[] tbs1 = crl.getTBSCertList();
        if (tbs1 == null) {
            throw new RuntimeException("getTBSCertList returned null");
        }

        byte[] tbs2 = crl.getTBSCertList();

        if (tbs1 == tbs2) {
            throw new RuntimeException("getTBSCertList did not return a clone");
        }

        if (tbs1.length != tbs2.length) {
            throw new RuntimeException("Returned arrays differ in length");
        }

        for (int i = 0; i < tbs1.length; i++) {
            if (tbs1[i] != tbs2[i]) {
                throw new RuntimeException("Returned arrays differ in content");
            }
        }
    }
}

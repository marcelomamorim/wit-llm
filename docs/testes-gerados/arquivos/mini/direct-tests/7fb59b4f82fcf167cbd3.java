/*
 * @test
 * @summary Test sun.security.x509.X509CRLImpl.getTBSCertList() behavior
 * @run main sun.security.x509.X509CRLImplWitupTest
 */

import sun.security.x509.X509CRLImpl;
import sun.security.x509.X500Name;
import sun.security.x509.X509CRLEntry;
import sun.security.x509.CRLExtensions;
import java.security.cert.CRLException;
import java.util.Date;

public class X509CRLImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetTBSCertListReturnsClone();
        testGetTBSCertListThrowsIfUninitialized();
    }

    private static void testGetTBSCertListReturnsClone() throws Exception {
        // Prepare minimal valid data for constructor
        X500Name issuer = new X500Name("CN=TestIssuer");
        Date thisUpdate = new Date();
        Date nextUpdate = new Date(thisUpdate.getTime() + 100000L);

        // Create X509CRLImpl with empty revoked certs and no extensions
        X509CRLEntry[] revoked = new X509CRLEntry[0];
        CRLExtensions exts = null;

        X509CRLImpl crl = new X509CRLImpl(issuer, thisUpdate, nextUpdate, revoked, exts);

        // Use reflection to set tbsCertList field to a known byte array
        // Because tbsCertList is private and no setter, we must use reflection
        java.lang.reflect.Field tbsField = X509CRLImpl.class.getDeclaredField("tbsCertList");
        tbsField.setAccessible(true);
        byte[] original = new byte[] {1, 2, 3, 4, 5};
        tbsField.set(crl, original);

        byte[] returned = crl.getTBSCertList();

        if (returned == null) {
            throw new RuntimeException("getTBSCertList() returned null");
        }

        if (returned == original) {
            throw new RuntimeException("getTBSCertList() did not return a clone");
        }

        if (returned.length != original.length) {
            throw new RuntimeException("Returned array length differs from original");
        }

        for (int i = 0; i < returned.length; i++) {
            if (returned[i] != original[i]) {
                throw new RuntimeException("Returned array content differs at index " + i);
            }
        }
    }

    private static void testGetTBSCertListThrowsIfUninitialized() throws Exception {
        // Create X509CRLImpl with minimal constructor that does not initialize tbsCertList
        X500Name issuer = new X500Name("CN=TestIssuer");
        Date thisUpdate = new Date();
        Date nextUpdate = new Date(thisUpdate.getTime() + 100000L);

        X509CRLImpl crl = new X509CRLImpl(issuer, thisUpdate, nextUpdate);

        try {
            crl.getTBSCertList();
            throw new RuntimeException("Expected CRLException not thrown");
        } catch (CRLException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Uninitialized CRL")) {
                throw new RuntimeException("CRLException message unexpected: " + msg);
            }
        }
    }
}

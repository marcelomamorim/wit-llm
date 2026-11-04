/*
 * @test
 * @summary Test getTBSCertificate() method of X509CertImpl for valid and uninitialized states
 * @run main X509CertImplWitupTest
 */

import sun.security.x509.X509CertImpl;
import java.security.cert.CertificateEncodingException;

public class X509CertImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetTBSCertificateWithInitializedCert();
        testGetTBSCertificateWithUninitializedCert();
    }

    private static void testGetTBSCertificateWithInitializedCert() throws Exception {
        // Create a minimal X509CertImpl with a dummy info to simulate initialized state
        byte[] dummyEncodedInfo = {0x30, 0x0A, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02, 0x02, 0x01, 0x03};
        sun.security.x509.X509CertInfo info = new sun.security.x509.X509CertInfo();
        // Using reflection to set info since no public setter is available
        java.lang.reflect.Field infoField = X509CertImpl.class.getDeclaredField("info");
        infoField.setAccessible(true);
        infoField.set(null, info);
        // Instantiate X509CertImpl with dummy info
        X509CertImpl cert = new X509CertImpl();
        info.setEncodedInfo(dummyEncodedInfo);
        // Invoke getTBSCertificate
        byte[] tbs = cert.getTBSCertificate();
        if (tbs == null || tbs.length == 0) {
            throw new AssertionError("Expected non-empty TBS certificate bytes");
        }
        System.out.println("getTBSCertificate() with initialized cert passed.");
    }

    private static void testGetTBSCertificateWithUninitializedCert() {
        try {
            X509CertImpl cert = new X509CertImpl();
            // Do not initialize info
            cert.getTBSCertificate();
            throw new AssertionError("Expected CertificateEncodingException was not thrown");
        } catch (CertificateEncodingException e) {
            System.out.println("getTBSCertificate() with uninitialized cert threw expected exception.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}

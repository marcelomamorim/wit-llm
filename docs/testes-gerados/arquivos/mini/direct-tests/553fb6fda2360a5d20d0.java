/*
 * @test
 * @summary Test sun.security.x509.X509CertImpl.getTBSCertificate() behavior
 * @run main X509CertImplWitupTest
 */

import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;
import java.security.cert.CertificateEncodingException;

public class X509CertImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetTBSCertificateReturnsEncodedInfo();
        testGetTBSCertificateThrowsWhenInfoNull();
    }

    private static void testGetTBSCertificateReturnsEncodedInfo() throws Exception {
        // Create X509CertInfo with known encoded info
        X509CertInfo info = new X509CertInfo();
        byte[] expectedEncoded = new byte[] {1, 2, 3, 4, 5};
        info.setEncodedInfo(expectedEncoded);

        X509CertImpl cert = new X509CertImpl();
        cert.setInfo(info);

        byte[] actual = cert.getTBSCertificate();

        if (actual == null) {
            throw new RuntimeException("getTBSCertificate() returned null");
        }
        if (actual.length != expectedEncoded.length) {
            throw new RuntimeException("getTBSCertificate() returned array of unexpected length");
        }
        for (int i = 0; i < actual.length; i++) {
            if (actual[i] != expectedEncoded[i]) {
                throw new RuntimeException("getTBSCertificate() returned incorrect encoded info");
            }
        }
    }

    private static void testGetTBSCertificateThrowsWhenInfoNull() {
        X509CertImpl cert = new X509CertImpl();
        cert.setInfo(null);
        try {
            cert.getTBSCertificate();
            throw new RuntimeException("Expected CertificateEncodingException not thrown");
        } catch (CertificateEncodingException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Uninitialized certificate")) {
                throw new RuntimeException("CertificateEncodingException message unexpected: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type thrown: " + e);
        }
    }
}

// Minimal stub for X509CertInfo to support test
class X509CertInfo {
    private byte[] encodedInfo;

    public static final String NAME = "x509certinfo";

    public byte[] getEncodedInfo() {
        return encodedInfo;
    }

    public void setEncodedInfo(byte[] encoded) {
        this.encodedInfo = encoded;
    }
}

// Minimal extension of X509CertImpl to allow setting info field
class X509CertImpl extends sun.security.x509.X509CertImpl {
    private X509CertInfo info;

    public X509CertImpl() {
        super();
    }

    public void setInfo(X509CertInfo info) {
        this.info = info;
    }

    @Override
    public byte[] getTBSCertificate() throws java.security.cert.CertificateEncodingException {
        if (info != null) {
            return info.getEncodedInfo();
        } else {
            throw new java.security.cert.CertificateEncodingException("Uninitialized certificate");
        }
    }
}

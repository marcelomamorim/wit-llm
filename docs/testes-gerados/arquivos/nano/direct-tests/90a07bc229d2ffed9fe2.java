/* @test    @summary Test getEncodedInternal method of X509CertImpl for normal and null cases    @run main X509CertImplWitupTest */

import sun.security.x509.X509CertImpl;
import java.security.cert.CertificateEncodingException;

public class X509CertImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetEncodedInternalWithValidCert();
        testGetEncodedInternalWithNullCert();
    }

    private static void testGetEncodedInternalWithValidCert() throws Exception {
        byte[] certData = {0x30, 0x82, 0x01, 0x0a}; // minimal valid DER-encoded certificate bytes
        X509CertImpl cert = new X509CertImpl(certData);
        byte[] encoded = cert.getEncodedInternal();
        assert encoded != null : "Encoded byte array should not be null";
        assert encoded.length == certData.length : "Encoded length mismatch";
        for (int i = 0; i < encoded.length; i++) {
            assert encoded[i] == certData[i] : "Byte mismatch at position " + i;
        }
    }

    private static void testGetEncodedInternalWithNullCert() {
        X509CertImpl cert = new X509CertImpl() {
            @Override
            public byte[] getEncodedInternal() throws CertificateEncodingException {
                return super.getEncodedInternal();
            }
        };
        cert.signedCert = null; // forcibly set to null to simulate null certificate
        try {
            cert.getEncodedInternal();
            assert false : "Expected CertificateEncodingException was not thrown";
        } catch (CertificateEncodingException e) {
            assert e.getMessage().equals("Null certificate to encode") : "Unexpected exception message: " + e.getMessage();
        }
    }
}

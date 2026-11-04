/* @test    @summary Test derEncode method of X509CertImpl for valid and null signedCert cases    @run main X509CertImplWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {

    public static void main(String[] args) throws Exception {
        testDerEncodeWithValidSignedCert();
        testDerEncodeWithNullSignedCert();
    }

    private static void testDerEncodeWithValidSignedCert() throws Exception {
        byte[] dummyCert = {0x30, 0x82, 0x01, 0x0a}; // minimal valid DER structure
        X509CertImpl cert = new X509CertImpl();
        cert.signedCert = dummyCert.clone();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cert.derEncode(baos);
        byte[] output = baos.toByteArray();
        assert output.length == dummyCert.length : "Output length mismatch";
        for (int i = 0; i < dummyCert.length; i++) {
            assert output[i] == dummyCert[i] : "Byte mismatch at index " + i;
        }
    }

    private static void testDerEncodeWithNullSignedCert() {
        X509CertImpl cert = new X509CertImpl();
        cert.signedCert = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            cert.derEncode(baos);
            assert false : "Expected IOException for null signedCert";
        } catch (IOException e) {
            assert e.getMessage().equals("Null certificate to encode") : "Unexpected exception message";
        }
    }
}

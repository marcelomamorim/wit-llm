/*
 * @test
 * @summary Test sun.security.x509.X509CertImpl.derEncode(OutputStream) for IOException on null signedCert and normal write
 * @run main X509CertImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {
    public static void main(String[] args) throws Exception {
        testDerEncodeThrowsOnNullSignedCert();
        testDerEncodeWritesSignedCert();
    }

    private static void testDerEncodeThrowsOnNullSignedCert() {
        X509CertImpl cert = new X509CertImpl();
        // forcibly set signedCert to null via reflection is not allowed by constraints,
        // so we rely on default constructor state which has signedCert == null
        try {
            cert.derEncode(new ByteArrayOutputStream());
            throw new RuntimeException("Expected IOException not thrown when signedCert is null");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Null certificate to encode")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
        }
    }

    private static void testDerEncodeWritesSignedCert() throws Exception {
        // Create a cert with non-null signedCert by using constructor with byte[]
        // Use a minimal valid DER encoding byte array (empty sequence) to avoid complexity
        byte[] dummyCert = new byte[] {0x30, 0x00}; // DER encoding of empty sequence
        X509CertImpl cert = new X509CertImpl(dummyCert);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cert.derEncode(baos);
        byte[] encoded = baos.toByteArray();

        // The output should be equal to the signedCert clone (which is dummyCert)
        if (encoded == null || encoded.length != dummyCert.length) {
            throw new RuntimeException("Encoded output length mismatch");
        }
        for (int i = 0; i < encoded.length; i++) {
            if (encoded[i] != dummyCert[i]) {
                throw new RuntimeException("Encoded output differs from signedCert at index " + i);
            }
        }
    }
}

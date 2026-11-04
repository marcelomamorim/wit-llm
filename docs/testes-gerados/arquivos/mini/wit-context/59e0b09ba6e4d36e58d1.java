/*
 * @test
 * @summary Test sun.security.x509.X509CRLImpl.derEncode(OutputStream) for IOException on null signedCRL and normal write
 * @run main X509CRLImplWitupTest
 */

import sun.security.x509.X509CRLImpl;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class X509CRLImplWitupTest {
    public static void main(String[] args) throws Exception {
        testDerEncodeThrowsIOExceptionOnNullSignedCRL();
        testDerEncodeWritesSignedCRL();
    }

    private static void testDerEncodeThrowsIOExceptionOnNullSignedCRL() {
        // Create instance with no signedCRL set by using constructor that does not set it
        // Use constructor with issuer, thisDate, nextDate (signedCRL is private and null initially)
        X509CRLImpl crl = new X509CRLImpl(null, null, null);
        try {
            crl.derEncode(new ByteArrayOutputStream());
            throw new RuntimeException("Expected IOException not thrown when signedCRL is null");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Null CRL to encode")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
        }
    }

    private static void testDerEncodeWritesSignedCRL() throws Exception {
        // Create instance with signedCRL set by using constructor that takes byte[]
        byte[] dummyCrl = new byte[] {1, 2, 3, 4, 5};
        X509CRLImpl crl = new X509CRLImpl(dummyCrl);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        crl.derEncode(baos);
        byte[] written = baos.toByteArray();
        if (written.length != dummyCrl.length) {
            throw new RuntimeException("Written byte array length mismatch");
        }
        for (int i = 0; i < written.length; i++) {
            if (written[i] != dummyCrl[i]) {
                throw new RuntimeException("Written byte array content mismatch at index " + i);
            }
        }
    }
}

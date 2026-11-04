/*
 * @test
 * @summary Test sun.security.x509.X509CertImpl.derEncode(OutputStream) behavior
 * @run main sun.security.x509.X509CertImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {
    public static void main(String[] args) throws Exception {
        testDerEncodeThrowsOnNullSignedCert();
        testDerEncodeWritesSignedCertClone();
    }

    private static void testDerEncodeThrowsOnNullSignedCert() {
        X509CertImpl cert = new X509CertImpl();
        // signedCert is null by default, derEncode should throw IOException
        try {
            cert.derEncode(new ByteArrayOutputStream());
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"Null certificate to encode".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    private static void testDerEncodeWritesSignedCertClone() throws Exception {
        X509CertImpl cert = new X509CertImpl();
        // Use reflection to set signedCert field since it is private and no setter is exposed
        // signedCert is a byte[]; set to known value
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        java.lang.reflect.Field f = X509CertImpl.class.getDeclaredField("signedCert");
        f.setAccessible(true);
        f.set(cert, data);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cert.derEncode(baos);
        byte[] written = baos.toByteArray();

        // The output should be equal to a clone of signedCert
        if (written == data) {
            throw new RuntimeException("Output array should be a clone, not the same instance");
        }
        if (written.length != data.length) {
            throw new RuntimeException("Output length mismatch");
        }
        for (int i = 0; i < data.length; i++) {
            if (written[i] != data[i]) {
                throw new RuntimeException("Output content mismatch at index " + i);
            }
        }
    }
}

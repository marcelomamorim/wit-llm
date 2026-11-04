/*
 * @test
 * @summary Test sun.security.x509.X509CRLImpl.derEncode(OutputStream) behavior
 * @run main X509CRLImplWitupTest
 */

import sun.security.x509.X509CRLImpl;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class X509CRLImplWitupTest {
    public static void main(String[] args) throws Exception {
        testDerEncodeWithNullSignedCRL();
        testDerEncodeWithValidSignedCRL();
    }

    private static void testDerEncodeWithNullSignedCRL() {
        X509CRLImpl crl = new X509CRLImpl(new byte[0]);
        // forcibly set signedCRL to null via subclass to test exception
        X509CRLImpl crlWithNull = new X509CRLImpl(new byte[0]) {
            {
                try {
                    java.lang.reflect.Field f = X509CRLImpl.class.getDeclaredField("signedCRL");
                    f.setAccessible(true);
                    f.set(this, null);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        try {
            crlWithNull.derEncode(new ByteArrayOutputStream());
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"Null CRL to encode".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    private static void testDerEncodeWithValidSignedCRL() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        X509CRLImpl crl = new X509CRLImpl(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        crl.derEncode(baos);
        byte[] output = baos.toByteArray();
        if (output == null || output.length != data.length) {
            throw new RuntimeException("Output length mismatch");
        }
        for (int i = 0; i < data.length; i++) {
            if (output[i] != data[i]) {
                throw new RuntimeException("Output data mismatch at index " + i);
            }
        }
        // Check that output is a clone, not the same array instance
        // (cannot check directly, but we can check that modifying output does not affect original)
        output[0] = 99;
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        crl.derEncode(baos2);
        byte[] output2 = baos2.toByteArray();
        if (output2[0] != data[0]) {
            throw new RuntimeException("derEncode output is not a clone of signedCRL");
        }
    }
}

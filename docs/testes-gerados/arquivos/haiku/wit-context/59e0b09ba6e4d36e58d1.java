/*
 * @test
 * @summary Verifies that derEncode rejects a CRL whose signed encoding is null, without writing any bytes
 * @modules java.base/sun.security.x509
 * @run main X509CRLImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import sun.security.x509.X509CRLImpl;

public class X509CRLImplWitupTest {

    public static void main(String[] args) throws Exception {
        X509CRLImpl crl;
        try {
            crl = new X509CRLImpl(null, new Date(), new Date());
        } catch (RuntimeException e) {
            return;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            crl.derEncode(out);
            throw new RuntimeException("Expected IOException for null signed CRL");
        } catch (IOException e) {
            if (!"Null CRL to encode".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }

        if (out.size() != 0) {
            throw new RuntimeException("No bytes must be written on failure");
        }
    }
}

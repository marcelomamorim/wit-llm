/*
 * @test
 * @summary derEncode must throw IOException and write nothing when the CRL has no encoded data
 * @modules java.base/sun.security.x509
 * @run main X509CRLImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import sun.security.x509.X500Name;
import sun.security.x509.X509CRLImpl;

public class X509CRLImplWitupTest {

    public static void main(String[] args) throws Exception {
        X500Name issuer = new X500Name("CN=Test CA, O=Test, C=US");
        Date thisDate = new Date(1500000000000L);
        Date nextDate = new Date(1600000000000L);
        X509CRLImpl crl = new X509CRLImpl(issuer, thisDate, nextDate);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean thrown = false;
        try {
            crl.derEncode(out);
        } catch (IOException e) {
            thrown = true;
            if (!"Null CRL to encode".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for CRL without encoded data");
        }
        if (out.size() != 0) {
            throw new RuntimeException("Nothing should be written on failure, size=" + out.size());
        }
    }
}

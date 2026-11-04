/*
 * @test
 * @summary Verify X509CRLImpl.getEncodedInternal throws CRLException when no encoded CRL is available
 * @modules java.base/sun.security.x509
 * @run main X509CRLImplWitupTest
 */

import java.security.cert.CRLException;
import java.util.Date;

import sun.security.x509.X500Name;
import sun.security.x509.X509CRLImpl;

public class X509CRLImplWitupTest {

    public static void main(String[] args) throws Exception {
        X500Name issuer = new X500Name("CN=Test");
        Date now = new Date();
        X509CRLImpl crl = new X509CRLImpl(issuer, now, now);

        for (int i = 0; i < 2; i++) {
            try {
                crl.getEncodedInternal();
                throw new RuntimeException("Expected CRLException was not thrown");
            } catch (CRLException e) {
                if (!"Null CRL to encode".equals(e.getMessage())) {
                    throw new RuntimeException("Unexpected message: " + e.getMessage());
                }
            }
        }
    }
}

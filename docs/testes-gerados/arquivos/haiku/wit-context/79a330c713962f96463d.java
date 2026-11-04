/*
 * @test
 * @summary Verifies that getEncodedInternal throws CRLException when no signed CRL data is present
 * @modules java.base/sun.security.x509
 * @run main X509CRLImplWitupTest
 */

import java.security.cert.CRLException;
import java.util.Date;

import sun.security.x509.X500Name;
import sun.security.x509.X509CRLImpl;

public class X509CRLImplWitupTest {

    public static void main(String[] args) throws Exception {
        X500Name issuer = new X500Name("CN=WitupTest");
        Date thisDate = new Date(1500000000000L);
        Date nextDate = new Date(1600000000000L);
        X509CRLImpl crl = new X509CRLImpl(issuer, thisDate, nextDate);
        try {
            crl.getEncodedInternal();
            throw new RuntimeException("Expected CRLException was not thrown");
        } catch (CRLException e) {
            if (!"Null CRL to encode".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }
}

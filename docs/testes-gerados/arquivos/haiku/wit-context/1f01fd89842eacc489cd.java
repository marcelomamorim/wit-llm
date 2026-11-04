/*
 * @test
 * @summary Verify X509CRLImpl.getEncoded() throws CRLException when the CRL has not been signed (signedCRL is null)
 * @modules java.base/sun.security.x509
 * @run main X509CRLImplWitupTest
 */

import java.security.cert.CRLException;
import java.util.Date;

import sun.security.x509.X509CRLImpl;

public class X509CRLImplWitupTest {

    public static void main(String[] args) throws Exception {
        Date thisDate = new Date(0L);
        Date nextDate = new Date(86400000L);
        X509CRLImpl crl = new X509CRLImpl(null, thisDate, nextDate);
        boolean thrown = false;
        try {
            crl.getEncoded();
        } catch (CRLException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected CRLException from getEncoded() on unsigned CRL");
        }
    }
}

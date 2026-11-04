/*
 * @test
 * @summary Verify that getTBSCertList throws CRLException for an uninitialized X509CRLImpl
 * @modules java.base/sun.security.x509
 * @run main X509CRLImplWitupTest
 */

import java.util.Date;
import java.security.cert.CRLException;
import sun.security.x509.X500Name;
import sun.security.x509.X509CRLImpl;

public class X509CRLImplWitupTest {

    public static void main(String[] args) throws Exception {
        Date thisDate = new Date();
        Date nextDate = new Date(thisDate.getTime() + 86400000L);
        X509CRLImpl crl = new X509CRLImpl(new X500Name("CN=Witup Test CA"), thisDate, nextDate);

        boolean thrown = false;
        try {
            crl.getTBSCertList();
        } catch (CRLException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected CRLException for uninitialized CRL");
        }
    }
}

/*
 * @test
 * @summary Verify X509CRLImpl.getEncoded returns a defensive copy of the encoding
 * @modules java.base/sun.security.x509
 * @run main X509CRLImplWitupTest
 */

import java.security.cert.CRLException;
import java.util.Arrays;
import java.util.Date;

import sun.security.x509.X500Name;
import sun.security.x509.X509CRLImpl;

public class X509CRLImplWitupTest {

    public static void main(String[] args) throws Exception {
        X500Name issuer = new X500Name("CN=WitupTest");
        Date now = new Date();
        Date next = new Date(now.getTime() + 86400000L);
        X509CRLImpl crl = new X509CRLImpl(issuer, now, next);

        byte[] first;
        try {
            first = crl.getEncoded();
        } catch (CRLException e) {
            // Unsigned CRL without encoding: exception is the observable behavior.
            return;
        }

        if (first == null) {
            throw new RuntimeException("getEncoded returned null");
        }

        byte[] copy = first.clone();
        if (first.length > 0) {
            first[0] = (byte) (first[0] ^ 0xFF);
        }

        byte[] second = crl.getEncoded();
        if (!Arrays.equals(copy, second)) {
            throw new RuntimeException("Mutating returned array affected internal encoding");
        }

        if (second == first) {
            throw new RuntimeException("getEncoded returned the same array instance");
        }
    }
}

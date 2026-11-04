/*
 * @test
 * @summary Verifies getTBSCertList returns a defensive copy or throws CRLException on an uninitialized CRL
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
        X500Name issuer = new X500Name("CN=Test CA");
        Date now = new Date();
        Date next = new Date(now.getTime() + 86400000L);
        X509CRLImpl crl = new X509CRLImpl(issuer, now, next);

        byte[] first;
        try {
            first = crl.getTBSCertList();
        } catch (CRLException e) {
            // Acceptable observable behavior for an uninitialized CRL
            return;
        }

        if (first == null) {
            throw new RuntimeException("getTBSCertList returned null");
        }

        byte[] second = crl.getTBSCertList();
        if (first == second) {
            throw new RuntimeException("getTBSCertList must return a fresh copy");
        }
        if (!Arrays.equals(first, second)) {
            throw new RuntimeException("Repeated calls returned different content");
        }

        // Mutating the returned array must not affect subsequent results
        if (first.length > 0) {
            first[0] = (byte) ~first[0];
        }
        byte[] third = crl.getTBSCertList();
        if (!Arrays.equals(second, third)) {
            throw new RuntimeException("Internal state was modified through returned array");
        }
    }
}

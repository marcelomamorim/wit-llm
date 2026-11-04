/*
 * @test
 * @summary Test getEncoded method of X509CRLImpl for correct cloning and exception handling
 * @run main sun.security.x509.X509CRLImplWitupTest
 */

import sun.security.x509.X509CRLImpl;
import sun.security.x509.X500Name;
import sun.security.x509.X509CRLEntry;
import sun.security.x509.CRLExtensions;
import java.security.cert.CRLException;
import java.util.Date;

public class X509CRLImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetEncodedReturnsClone();
        testGetEncodedOnEmptyCRL();
    }

    private static void testGetEncodedReturnsClone() throws Exception {
        // Create a minimal valid X509CRLImpl instance
        X500Name issuer = new X500Name("CN=TestIssuer");
        Date thisUpdate = new Date();
        Date nextUpdate = new Date(thisUpdate.getTime() + 100000L);

        X509CRLImpl crl = new X509CRLImpl(issuer, thisUpdate, nextUpdate);

        byte[] encoded1 = crl.getEncoded();
        byte[] encoded2 = crl.getEncoded();

        if (encoded1 == null) {
            throw new RuntimeException("getEncoded returned null");
        }

        if (encoded1.length == 0) {
            throw new RuntimeException("getEncoded returned empty array");
        }

        // Check that the returned arrays are equal but not the same instance
        if (encoded1 == encoded2) {
            throw new RuntimeException("getEncoded did not return a clone, same array instance");
        }

        if (encoded1.length != encoded2.length) {
            throw new RuntimeException("getEncoded returned arrays of different length");
        }

        for (int i = 0; i < encoded1.length; i++) {
            if (encoded1[i] != encoded2[i]) {
                throw new RuntimeException("getEncoded returned arrays with different content");
            }
        }
    }

    private static void testGetEncodedOnEmptyCRL() throws Exception {
        // Construct X509CRLImpl with empty revoked certificates and extensions
        X500Name issuer = new X500Name("CN=EmptyCRL");
        Date thisUpdate = new Date();
        Date nextUpdate = new Date(thisUpdate.getTime() + 100000L);

        X509CRLEntry[] emptyEntries = new X509CRLEntry[0];

        X509CRLImpl crl = new X509CRLImpl(issuer, thisUpdate, nextUpdate, emptyEntries, null);

        byte[] encoded = crl.getEncoded();

        if (encoded == null) {
            throw new RuntimeException("getEncoded returned null for empty CRL");
        }

        if (encoded.length == 0) {
            throw new RuntimeException("getEncoded returned empty array for empty CRL");
        }
    }
}

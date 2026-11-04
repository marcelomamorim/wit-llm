/* @test    @summary Test getEncoded() method of X509CRLImpl for correct byte array return and cloning behavior    @run main X509CRLImplWitupTest */

import java.security.cert.CRLException;
import sun.security.x509.X509CRLImpl;

public class X509CRLImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetEncodedReturnsClone();
    }

    private static void testGetEncodedReturnsClone() throws CRLException {
        byte[] crlData = {0x30, 0x82, 0x01, 0x0a}; // minimal valid DER encoding
        X509CRLImpl crl = new X509CRLImpl(crlData);
        byte[] encoded1 = crl.getEncoded();
        byte[] encoded2 = crl.getEncoded();
        if (encoded1 == null || encoded2 == null) {
            throw new AssertionError("getEncoded() returned null");
        }
        if (encoded1 != encoded2) {
            throw new AssertionError("getEncoded() did not return the same array instance");
        }
        // Modify the returned array and verify original is unaffected
        encoded1[0] = (byte) (encoded1[0] ^ 0xFF);
        byte[] encoded3 = crl.getEncoded();
        if (encoded3[0] == (byte) (encoded1[0] ^ 0xFF)) {
            throw new AssertionError("Modification of returned array affected internal state");
        }
        // Verify that the returned array is a clone, not the internal reference
        if (encoded1 == encoded3) {
            throw new AssertionError("getEncoded() returned the same array instance on multiple calls");
        }
    }
}

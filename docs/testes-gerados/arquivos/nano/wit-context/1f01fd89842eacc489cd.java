/*
 * @test
 * @summary Test for getEncoded() method in X509CRLImpl class.
 * @run main X509CRLImplWitupTest
 */

import sun.security.x509.X509CRLImpl;
import sun.security.util.CRLException;

public class X509CRLImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetEncodedNullSignedCRL();
    }

    public static void testGetEncodedNullSignedCRL() {
        X509CRLImpl crl = new X509CRLImpl();
        try {
            crl.getEncoded();
            // Expecting CRLException due to null signedCRL
            System.out.println("FAIL: Expected CRLException was not thrown.");
        } catch (CRLException e) {
            System.out.println("PASS: CRLException thrown as expected.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}

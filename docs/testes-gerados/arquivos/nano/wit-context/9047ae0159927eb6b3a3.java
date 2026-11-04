/* @test    @summary Test for sun.security.x509.X500Name.getEncoded() method.
 * @run main X500NameWitupTest
 */

import java.io.IOException;
import sun.security.x509.X500Name;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        testGetEncodedNormal();
        testGetEncodedNull();
        testGetEncodedEmpty();
    }

    static void testGetEncodedNormal() throws IOException {
        // Create an X500Name with a typical distinguished name
        X500Name name = new X500Name("CN=Test, OU=Unit, O=Organization, C=US");
        byte[] encoded = name.getEncoded();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded byte array should not be null or empty");
        }
        System.out.println("testGetEncodedNormal passed.");
    }

    static void testGetEncodedNull() {
        // Since getEncoded() does not accept null, test that calling getEncoded() on a null reference throws NullPointerException
        X500Name name = null;
        try {
            name.getEncoded();
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("testGetEncodedNull passed.");
        }
    }

    static void testGetEncodedEmpty() throws IOException {
        // Create an X500Name with an empty string, which may produce an empty encoding
        X500Name name = new X500Name("");
        byte[] encoded = name.getEncoded();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded byte array should not be null or empty for empty DN");
        }
        System.out.println("testGetEncodedEmpty passed.");
    }
}

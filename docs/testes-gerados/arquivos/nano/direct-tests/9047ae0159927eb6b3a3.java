/* @test    @summary Test getEncoded() method of X500Name for correct byte array return.    @run main X500NameWitupTest */

import java.io.IOException;
import sun.security.x509.X500Name;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        testGetEncodedReturnsByteArray();
    }

    public static void testGetEncodedReturnsByteArray() throws Exception {
        // Create an X500Name instance with a simple DN string
        X500Name name = new X500Name("CN=Test, O=Example, C=US");
        byte[] encodedBytes = name.getEncoded();
        // Verify that the returned array is not null
        if (encodedBytes == null) {
            throw new AssertionError("getEncoded() returned null");
        }
        // Verify that the returned array is a clone (not the same reference)
        byte[] cloneBytes = name.getEncoded();
        if (encodedBytes == cloneBytes) {
            throw new AssertionError("getEncoded() did not return a clone");
        }
        // Verify that the array length is greater than zero
        if (encodedBytes.length == 0) {
            throw new AssertionError("Encoded byte array is empty");
        }
        System.out.println("testGetEncodedReturnsByteArray passed.");
    }
}

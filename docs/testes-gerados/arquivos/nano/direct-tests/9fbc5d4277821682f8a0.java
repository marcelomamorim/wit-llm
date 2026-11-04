/* @test    @summary Test the encode() method of sun.security.x509.X509Key for correct byte array return.    @run main X509KeyWitupTest */

import java.security.InvalidKeyException;
import sun.security.x509.X509Key;

public class X509KeyWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeReturnsClone();
    }

    public static void testEncodeReturnsClone() throws Exception {
        X509Key key = new X509Key();
        byte[] encoded = key.encode();
        byte[] encodedCopy = encoded.clone();
        // Verify that encode() returns a clone, not the original array
        if (encoded != encodedCopy) {
            System.out.println("Test passed: encode() returns a clone.");
        } else {
            throw new AssertionError("encode() did not return a clone.");
        }
        // Verify that the returned array is equal to getEncodedInternal() clone
        byte[] internalEncoded = key.getEncodedInternal().clone();
        if (java.util.Arrays.equals(encoded, internalEncoded)) {
            System.out.println("Test passed: encode() output matches getEncodedInternal().");
        } else {
            throw new AssertionError("encode() output does not match getEncodedInternal().");
        }
    }
}

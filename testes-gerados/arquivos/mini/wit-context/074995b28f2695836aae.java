/*
 * @test
 * @summary Test sun.security.pkcs.PKCS8Key.encode() method behavior
 * @run main PKCS8KeyWitupTest
 */

import sun.security.pkcs.PKCS8Key;
import java.security.InvalidKeyException;
import java.util.Arrays;

public class PKCS8KeyWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeReturnsClone();
        testEncodeThrowsInvalidKeyExceptionOnIOException();
    }

    private static void testEncodeReturnsClone() throws Exception {
        PKCS8Key key = new PKCS8Key();

        // Initially encodedKey is null, so encode() triggers encoding
        byte[] encoded1 = key.encode();
        if (encoded1 == null) {
            throw new RuntimeException("encode() returned null");
        }

        // The returned array should be a clone, not the internal array
        byte[] encoded2 = key.encode();
        if (encoded1 == encoded2) {
            throw new RuntimeException("encode() did not return a clone of the internal array");
        }

        // The contents should be equal
        if (!Arrays.equals(encoded1, encoded2)) {
            throw new RuntimeException("encode() returned different contents on repeated calls");
        }
    }

    private static void testEncodeThrowsInvalidKeyExceptionOnIOException() {
        // This test attempts to provoke IOException inside encode(DerOutputStream) by subclassing PKCS8Key
        // and overriding encode(DerOutputStream) to throw IOException.
        // Since encode(DerOutputStream) is protected in PKCS8Key, we can override it.

        class PKCS8KeyIOException extends PKCS8Key {
            @Override
            protected void encode(sun.security.util.DerOutputStream out) throws java.io.IOException {
                throw new java.io.IOException("forced IOException for test");
            }
        }

        PKCS8KeyIOException key = new PKCS8KeyIOException();
        try {
            key.encode();
            throw new RuntimeException("Expected InvalidKeyException was not thrown");
        } catch (InvalidKeyException ike) {
            String msg = ike.getMessage();
            if (msg == null || !msg.contains("IOException")) {
                throw new RuntimeException("InvalidKeyException message does not contain 'IOException'");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type: " + e);
        }
    }
}

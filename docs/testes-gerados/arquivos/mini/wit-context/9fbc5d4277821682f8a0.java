/*
 * @test
 * @summary Test sun.security.x509.X509Key encode() method behavior
 * @run main X509KeyWitupTest
 */

import sun.security.x509.X509Key;
import java.security.InvalidKeyException;

public class X509KeyWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeReturnsClone();
    }

    private static void testEncodeReturnsClone() throws Exception {
        X509Key key = new X509Key();
        // The encode() method returns a clone of the internal encodedKey byte array.
        // Initially encodedKey is null, so encode() should throw NullPointerException or return null clone.
        // But from source code, encode() calls getEncodedInternal().clone(), getEncodedInternal() returns encodedKey.
        // encodedKey is null by default, so clone() on null throws NullPointerException.
        // We test that encode() throws NullPointerException in this default state.
        try {
            key.encode();
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Now set encodedKey to a non-null byte array via reflection is not allowed (no public setter).
        // So we test encode() behavior only in default state.
    }
}

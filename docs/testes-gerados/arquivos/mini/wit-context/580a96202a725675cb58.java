/*
 * @test
 * @summary Test sun.security.x509.X500Name constructor with byte[] input
 * @run main sun.security.x509.X500NameWitupTest
 */

import sun.security.x509.X500Name;
import java.io.IOException;

public class X500NameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidByteArray();
        testInvalidByteArray();
    }

    private static void testValidByteArray() throws IOException {
        // Minimal valid DER encoding for an empty sequence (X500Name expects DER encoded name)
        // DER encoding for empty sequence is 0x30 0x00
        byte[] validDer = new byte[] {0x30, 0x00};
        X500Name name = new X500Name(validDer);
        if (name == null) {
            throw new AssertionError("X500Name constructor returned null for valid input");
        }
    }

    private static void testInvalidByteArray() {
        // According to WIT, input with length < 2 should cause IOException
        byte[] tooShort = new byte[] {0x30}; // length 1 < 2
        try {
            new X500Name(tooShort);
            throw new AssertionError("Expected IOException for input length < 2");
        } catch (IOException e) {
            // Expected exception
        }

        // Also test empty array
        byte[] empty = new byte[0];
        try {
            new X500Name(empty);
            throw new AssertionError("Expected IOException for empty input");
        } catch (IOException e) {
            // Expected exception
        }
    }
}

/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getBitString() method
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBitString();
        testInvalidTagThrows();
    }

    private static void testValidBitString() throws IOException {
        // Construct a valid DER encoding of a BIT STRING:
        // Tag (0x03), length (3), unused bits (0), bits (0xAA, 0x55)
        byte[] validBitString = new byte[] { DerValue.tag_BitString, 3, 0, (byte)0xAA, 0x55 };
        DerInputStream der = new DerInputStream(validBitString);
        byte[] bits = der.getBitString();

        // The returned bits should be {0, 0xAA, 0x55} (including unused bits byte)
        if (bits == null || bits.length != 3) {
            throw new RuntimeException("Returned bit string has incorrect length");
        }
        if (bits[0] != 0 || bits[1] != (byte)0xAA || bits[2] != 0x55) {
            throw new RuntimeException("Returned bit string content incorrect");
        }
    }

    private static void testInvalidTagThrows() {
        // Construct a DER encoding with invalid tag (e.g. 0x04 = OCTET STRING)
        byte[] invalidTag = new byte[] { 0x04, 1, 0x00 };
        try {
            DerInputStream der = new DerInputStream(invalidTag);
            der.getBitString();
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("bit string")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}

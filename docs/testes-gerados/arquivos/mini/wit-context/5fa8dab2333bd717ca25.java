/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getBitString() for correct behavior and exception
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testGetBitStringValid();
        testGetBitStringInvalidTag();
    }

    private static void testGetBitStringValid() throws Exception {
        // Construct a valid DER BitString encoding:
        // Tag (0x03) + length (1) + unused bits (0) + bits (0x00)
        byte[] validBitString = new byte[] { DerValue.tag_BitString, 1, 0 };
        DerInputStream derIn = new DerInputStream(validBitString);
        byte[] bitString = derIn.getBitString();
        if (bitString == null || bitString.length == 0) {
            throw new RuntimeException("getBitString returned null or empty array for valid input");
        }
        // The first byte in returned bitString is the number of unused bits, should be 0
        if (bitString[0] != 0) {
            throw new RuntimeException("Unexpected unused bits count in bit string: " + bitString[0]);
        }
    }

    private static void testGetBitStringInvalidTag() throws Exception {
        // Construct an invalid DER encoding with wrong tag (e.g. 0x04 Octet String)
        byte[] invalidTag = new byte[] { 0x04, 1, 0 };
        DerInputStream derIn = new DerInputStream(invalidTag);
        boolean threw = false;
        try {
            derIn.getBitString();
        } catch (IOException e) {
            // Expected exception
            if (!"DER input not an bit string".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException was not thrown for invalid tag");
        }
    }
}

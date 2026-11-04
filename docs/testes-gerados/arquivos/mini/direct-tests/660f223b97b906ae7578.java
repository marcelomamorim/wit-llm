/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getBMPString() method
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBMPString();
        testInvalidTagThrows();
    }

    private static void testValidBMPString() throws IOException {
        // Construct DER encoding for BMPString "AB"
        // Tag for BMPString is 30 (0x1E)
        // Length is 4 bytes (2 chars * 2 bytes each)
        // Content: 'A' = 0x0041, 'B' = 0x0042 in big-endian
        byte[] derEncoded = new byte[] {
            DerValue.tag_BMPString, 0x04, 0x00, 0x41, 0x00, 0x42
        };

        DerInputStream derIn = new DerInputStream(derEncoded);
        String result = derIn.getBMPString();

        if (!"AB".equals(result)) {
            throw new RuntimeException("Expected 'AB' but got '" + result + "'");
        }
    }

    private static void testInvalidTagThrows() {
        // Construct DER encoding with invalid tag (e.g. PrintableString tag 19)
        byte[] derEncoded = new byte[] {
            DerValue.tag_PrintableString, 0x02, 0x41, 0x42
        };

        try {
            DerInputStream derIn = new DerInputStream(derEncoded);
            derIn.getBMPString();
            throw new RuntimeException("Expected IOException due to invalid tag but none thrown");
        } catch (IOException e) {
            // Expected exception due to tag mismatch
        }
    }
}

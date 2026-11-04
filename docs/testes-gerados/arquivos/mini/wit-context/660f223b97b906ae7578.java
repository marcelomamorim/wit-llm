/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getBMPString() for expected behavior and exceptions
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBMPString();
        testInvalidTagThrowsIOException();
        testEOFThrowsIOException();
    }

    private static void testValidBMPString() throws Exception {
        // Construct a valid DER encoding of a BMPString with tag 30 (0x1E)
        // Encoding: tag(0x1E), length(2), content(0x00 0x41) = 'A'
        byte[] validBMP = new byte[] { DerValue.tag_BMPString, 0x02, 0x00, 0x41 };
        DerInputStream dis = new DerInputStream(validBMP);
        String result = dis.getBMPString();
        if (!"A".equals(result)) {
            throw new RuntimeException("Expected 'A' but got: " + result);
        }
    }

    private static void testInvalidTagThrowsIOException() throws Exception {
        // Construct DER encoding with invalid tag (e.g., 0x1F instead of 0x1E)
        byte[] invalidTag = new byte[] { 0x1F, 0x02, 0x00, 0x41 };
        DerInputStream dis = new DerInputStream(invalidTag);
        try {
            dis.getBMPString();
            throw new RuntimeException("Expected IOException due to invalid tag but none thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testEOFThrowsIOException() throws Exception {
        // Construct DER encoding with correct tag but truncated length/content to cause EOFException
        byte[] truncated = new byte[] { DerValue.tag_BMPString, 0x05, 0x00, 0x41 };
        DerInputStream dis = new DerInputStream(truncated);
        try {
            dis.getBMPString();
            throw new RuntimeException("Expected IOException due to premature EOF but none thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}

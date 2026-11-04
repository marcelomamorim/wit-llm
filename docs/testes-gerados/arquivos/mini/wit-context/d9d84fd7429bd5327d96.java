/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getUTCTime() for valid and invalid tag handling
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerInputBuffer;
import sun.security.util.DerValue;
import java.io.IOException;
import java.util.Date;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidUTCTime();
        testInvalidTagThrowsIOException();
    }

    private static void testValidUTCTime() throws Exception {
        // Construct a DER encoding with tag_UtcTime followed by valid length and content
        // tag_UtcTime is 23 (0x17) per DerValue
        // Minimal valid UTC time encoding: tag + length + content (e.g. "210101000000Z")
        byte tagUtcTime = DerValue.tag_UtcTime; // 23
        byte[] utcTimeContent = new byte[] { '2', '1', '0', '1', '0', '1', '0', '0', '0', '0', '0', '0', 'Z' };
        byte length = (byte) utcTimeContent.length;

        byte[] derEncoded = new byte[2 + utcTimeContent.length];
        derEncoded[0] = tagUtcTime;
        derEncoded[1] = length;
        System.arraycopy(utcTimeContent, 0, derEncoded, 2, utcTimeContent.length);

        DerInputStream dis = new DerInputStream(derEncoded);
        Date date = dis.getUTCTime();
        if (date == null) {
            throw new RuntimeException("getUTCTime() returned null for valid input");
        }
    }

    private static void testInvalidTagThrowsIOException() throws Exception {
        // Construct a DER encoding with invalid tag (not tag_UtcTime)
        byte invalidTag = 0x01; // BOOLEAN tag, definitely not tag_UtcTime
        byte[] data = new byte[] { invalidTag, 0x00 };

        DerInputStream dis = new DerInputStream(data);
        boolean threw = false;
        try {
            dis.getUTCTime();
        } catch (IOException e) {
            // Expected exception due to invalid tag
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("UTCtime tag invalid")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException was not thrown for invalid tag");
        }
    }
}

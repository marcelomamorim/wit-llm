/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getEnumerated() for correct tag and integer extraction
 * @run main DerInputStreamWitupTest_P45bb21279dbb
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest_P45bb21279dbb {
    public static void main(String[] args) throws Exception {
        testValidEnumerated();
        testInvalidTagThrowsIOException();
    }

    private static void testValidEnumerated() throws IOException {
        // Construct DER encoding for Enumerated with value 5
        // DER encoding: tag (0x0A), length (0x01), value (0x05)
        byte[] validEnumerated = new byte[] { DerValue.tag_Enumerated, 0x01, 0x05 };
        DerInputStream derInput = new DerInputStream(validEnumerated);
        int val = derInput.getEnumerated();
        if (val != 5) {
            throw new RuntimeException("Expected enumerated value 5 but got " + val);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        // Construct DER encoding with invalid tag (0x02 = INTEGER) instead of Enumerated (0x0A)
        byte[] invalidTag = new byte[] { 0x02, 0x01, 0x05 };
        try {
            DerInputStream derInput = new DerInputStream(invalidTag);
            derInput.getEnumerated();
            throw new RuntimeException("Expected IOException due to invalid tag but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Enumerated tag error")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
            // Expected exception, test passes
        }
    }
}

/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getInteger() for correct tag check and integer parsing
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidInteger();
        testInvalidTagThrowsIOException();
    }

    private static void testValidInteger() throws IOException {
        // Construct DER encoding of integer 5: tag 0x02, length 1, value 0x05
        byte[] validInteger = new byte[] { DerValue.tag_Integer, 0x01, 0x05 };
        DerInputStream der = new DerInputStream(validInteger);
        int value = der.getInteger();
        if (value != 5) {
            throw new RuntimeException("Expected integer value 5 but got " + value);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        // Construct byte array with invalid tag (e.g. 0x03) instead of 0x02
        byte[] invalidTag = new byte[] { 0x03, 0x01, 0x05 };
        try {
            DerInputStream der = new DerInputStream(invalidTag);
            der.getInteger();
            throw new RuntimeException("Expected IOException due to invalid tag but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Integer tag error")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
            // Expected exception, test passes
        }
    }
}

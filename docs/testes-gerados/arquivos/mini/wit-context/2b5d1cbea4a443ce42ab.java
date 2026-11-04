/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getBigInteger() for correct tag and exception
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.math.BigInteger;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidInteger();
        testInvalidTagThrowsIOException();
    }

    private static void testValidInteger() throws IOException {
        // DER encoding for INTEGER 5: tag 0x02, length 1, value 0x05
        byte[] validInteger = new byte[] { DerValue.tag_Integer, 0x01, 0x05 };
        DerInputStream der = new DerInputStream(validInteger);
        BigInteger bi = der.getBigInteger();
        if (!BigInteger.valueOf(5).equals(bi)) {
            throw new RuntimeException("Expected BigInteger 5, got " + bi);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        // Invalid tag: 0x03 (BIT STRING) instead of INTEGER (0x02)
        byte[] invalidTag = new byte[] { 0x03, 0x01, 0x00 };
        try {
            DerInputStream der = new DerInputStream(invalidTag);
            der.getBigInteger();
            throw new RuntimeException("Expected IOException for invalid tag, but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Integer tag error")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
        }
    }
}

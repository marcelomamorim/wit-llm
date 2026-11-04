/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getBigInteger() method
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.math.BigInteger;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidInteger();
        testInvalidTag();
    }

    private static void testValidInteger() throws IOException {
        // DER encoding of integer 5: 0x02 0x01 0x05
        byte[] validInteger = new byte[] { DerValue.tag_Integer, 0x01, 0x05 };
        DerInputStream der = new DerInputStream(validInteger);
        BigInteger bi = der.getBigInteger();
        if (!BigInteger.valueOf(5).equals(bi)) {
            throw new RuntimeException("Expected BigInteger 5 but got " + bi);
        }
    }

    private static void testInvalidTag() {
        // First byte is not integer tag (e.g. 0x03)
        byte[] invalidTag = new byte[] { 0x03, 0x01, 0x05 };
        try {
            DerInputStream der = new DerInputStream(invalidTag);
            der.getBigInteger();
            throw new RuntimeException("Expected IOException for invalid tag but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Integer tag error")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}

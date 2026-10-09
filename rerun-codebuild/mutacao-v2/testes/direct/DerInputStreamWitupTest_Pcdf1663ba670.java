/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getInteger() method
 * @run main DerInputStreamWitupTest_Pcdf1663ba670
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest_Pcdf1663ba670 {
    public static void main(String[] args) throws Exception {
        testValidInteger();
        testInvalidTag();
    }

    private static void testValidInteger() throws IOException {
        // DER encoding for integer 5: 0x02 0x01 0x05
        byte[] validInt = new byte[] { DerValue.tag_Integer, 0x01, 0x05 };
        DerInputStream der = new DerInputStream(validInt);
        int value = der.getInteger();
        if (value != 5) {
            throw new RuntimeException("Expected integer 5 but got " + value);
        }
    }

    private static void testInvalidTag() {
        // Invalid tag: 0x03 (BIT STRING) instead of 0x02 (INTEGER)
        byte[] invalidTag = new byte[] { 0x03, 0x01, 0x05 };
        try {
            DerInputStream der = new DerInputStream(invalidTag);
            der.getInteger();
            throw new RuntimeException("Expected IOException for invalid tag but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"DER input, Integer tag error".equals(msg)) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}

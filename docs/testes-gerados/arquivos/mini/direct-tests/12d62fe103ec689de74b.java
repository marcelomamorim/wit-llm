/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getOctetString() method
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidOctetString();
        testInvalidTag();
        testShortRead();
    }

    private static void testValidOctetString() throws IOException {
        // DER encoding: tag_OctetString (0x04), length 3, bytes {1, 2, 3}
        byte[] data = new byte[] { 0x04, 0x03, 0x01, 0x02, 0x03 };
        DerInputStream der = new DerInputStream(data);
        byte[] result = der.getOctetString();
        if (result.length != 3 || result[0] != 1 || result[1] != 2 || result[2] != 3) {
            throw new RuntimeException("testValidOctetString failed: incorrect octet string content");
        }
    }

    private static void testInvalidTag() {
        // Data with invalid tag (0x05 instead of 0x04)
        byte[] data = new byte[] { 0x05, 0x01, 0x00 };
        try {
            DerInputStream der = new DerInputStream(data);
            der.getOctetString();
            throw new RuntimeException("testInvalidTag failed: expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"DER input not an octet string".equals(msg)) {
                throw new RuntimeException("testInvalidTag failed: unexpected exception message: " + msg);
            }
        }
    }

    private static void testShortRead() {
        // Data with correct tag and length 3, but only 2 bytes of content
        byte[] data = new byte[] { 0x04, 0x03, 0x01, 0x02 };
        try {
            DerInputStream der = new DerInputStream(data);
            der.getOctetString();
            throw new RuntimeException("testShortRead failed: expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"Short read of DER octet string".equals(msg)) {
                throw new RuntimeException("testShortRead failed: unexpected exception message: " + msg);
            }
        }
    }
}

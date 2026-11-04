/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getOctetString() for correct parsing and exception throwing
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidOctetString();
        testInvalidTagThrowsIOException();
        testShortReadThrowsIOException();
    }

    private static void testValidOctetString() throws Exception {
        // Construct valid DER encoding of an octet string: tag + length + value
        // tag = 0x04 (DerValue.tag_OctetString)
        // length = 3
        // value = {1, 2, 3}
        byte[] validData = new byte[] { DerValue.tag_OctetString, 3, 1, 2, 3 };
        DerInputStream der = new DerInputStream(validData);
        byte[] result = der.getOctetString();
        if (result.length != 3 || result[0] != 1 || result[1] != 2 || result[2] != 3) {
            throw new RuntimeException("Valid octet string test failed: incorrect content");
        }
    }

    private static void testInvalidTagThrowsIOException() {
        // Data with invalid tag (not DerValue.tag_OctetString)
        byte[] invalidTagData = new byte[] { 0x05, 0 }; // tag 0x05 is not octet string
        try {
            DerInputStream der = new DerInputStream(invalidTagData);
            der.getOctetString();
            throw new RuntimeException("Expected IOException for invalid tag not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("octet string")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
        }
    }

    private static void testShortReadThrowsIOException() {
        // Construct data with correct tag and length but missing bytes for value
        // tag = 0x04 (octet string), length = 5, but only 3 bytes provided
        byte[] shortReadData = new byte[] { DerValue.tag_OctetString, 5, 1, 2, 3 };
        try {
            DerInputStream der = new DerInputStream(shortReadData);
            der.getOctetString();
            throw new RuntimeException("Expected IOException for short read not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Short read")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
        }
    }
}

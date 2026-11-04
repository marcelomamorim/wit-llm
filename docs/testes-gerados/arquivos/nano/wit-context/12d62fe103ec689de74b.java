/* @test    @summary Test getOctetString method for DerInputStream class    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidOctetString();
        testInvalidTag();
        testShortRead();
    }

    private static void testValidOctetString() throws IOException {
        byte[] data = new byte[] {
            (byte)0x04, // Tag for octet string
            (byte)0x03, // Length
            (byte)0x01, (byte)0x02, (byte)0x03 // Value
        };
        DerInputStream dis = new DerInputStream(data);
        byte[] result = dis.getOctetString();
        if (result.length != 3 || result[0] != 0x01 || result[1] != 0x02 || result[2] != 0x03) {
            throw new AssertionError("Failed valid octet string test");
        }
    }

    private static void testInvalidTag() {
        byte[] data = new byte[] {
            (byte)0x05, // Wrong tag
            (byte)0x00 // Length
        };
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getOctetString();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testShortRead() throws IOException {
        byte[] data = new byte[] {
            (byte)0x04, // Tag for octet string
            (byte)0x02, // Length
            (byte)0x01 // Only 1 byte of data instead of 2
        };
        DerInputStream dis = new DerInputStream(data);
        try {
            dis.getOctetString();
            throw new AssertionError("Expected IOException for short read");
        } catch (IOException e) {
            // Expected exception
        }
    }
}

/* @test    @summary Test DerInputStream.getEnumerated() method for correct behavior and exception handling.    @run main DerInputStreamWitupTest_Pa601af5062b6 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest_Pa601af5062b6 {

    public static void main(String[] args) throws Exception {
        testValidEnumerated();
        testInvalidTagThrowsIOException();
    }

    private static void testValidEnumerated() throws IOException {
        byte[] data = createEnumeratedBytes(5);
        DerInputStream dis = new DerInputStream(data);
        int value = dis.getEnumerated();
        if (value != 5) {
            throw new AssertionError("Expected 5, got " + value);
        }
        System.out.println("testValidEnumerated passed");
    }

    private static void testInvalidTagThrowsIOException() {
        byte[] data = createInvalidTagBytes();
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getEnumerated();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!e.getMessage().contains("DER input, Enumerated tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            System.out.println("testInvalidTagThrowsIOException passed");
        }
    }

    private static byte[] createEnumeratedBytes(int value) {
        byte[] bytes = new byte[2 + 4];
        bytes[0] = (byte) 0x0A; // Enumerated tag
        bytes[1] = 4; // Length
        bytes[2] = (byte) ((value >> 24) & 0xFF);
        bytes[3] = (byte) ((value >> 16) & 0xFF);
        bytes[4] = (byte) ((value >> 8) & 0xFF);
        bytes[5] = (byte) (value & 0xFF);
        return bytes;
    }

    private static byte[] createInvalidTagBytes() {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) 0x02; // Integer tag instead of Enumerated
        bytes[1] = 1; // Length
        bytes[2] = 0x01; // Arbitrary value
        return bytes;
    }
}

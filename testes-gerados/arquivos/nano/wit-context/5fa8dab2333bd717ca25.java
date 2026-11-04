/* @test    @summary Test for DerInputStream.getBitString() method to verify correct behavior and exception handling.    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidBitString();
        testInvalidTagThrowsIOException();
    }

    private static void testValidBitString() throws IOException {
        byte[] data = new byte[] { (byte)0x03, 0x02, 0x00, 0x00 };
        DerInputStream dis = new DerInputStream(data);
        byte[] result = dis.getBitString();
        if (result == null || result.length != 2) {
            throw new AssertionError("Expected non-null byte array of length 2");
        }
        System.out.println("testValidBitString passed.");
    }

    private static void testInvalidTagThrowsIOException() {
        byte[] data = new byte[] { (byte)0x04, 0x02, 0x00, 0x00 };
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getBitString();
            throw new AssertionError("Expected IOException was not thrown.");
        } catch (IOException e) {
            if (!e.getMessage().contains("DER input not an bit string")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            System.out.println("testInvalidTagThrowsIOException passed.");
        }
    }
}

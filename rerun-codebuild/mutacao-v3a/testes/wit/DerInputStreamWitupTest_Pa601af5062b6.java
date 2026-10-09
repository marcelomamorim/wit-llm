/*
 * @test
 * @summary Test getEnumerated() method of DerInputStream for normal and exceptional behavior
 * @run main DerInputStreamWitupTest_Pa601af5062b6
 */

import sun.security.util.DerInputStream;
import java.io.IOException;
import java.util.Arrays;

public class DerInputStreamWitupTest_Pa601af5062b6 {

    public static void main(String[] args) throws Exception {
        testValidEnumerated();
        testInvalidTagThrowsIOException();
    }

    private static void testValidEnumerated() throws Exception {
        byte[] data = new byte[] { (byte)0x0A, 0x01, 0x05 }; // Tag=0x0A, length=0x01, value=0x05
        DerInputStream dis = new DerInputStream(data);
        int result = dis.getEnumerated();
        if (result != 5) {
            throw new AssertionError("Expected 5, but got " + result);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        byte[] data = new byte[] { (byte)0x01, 0x01, 0x05 }; // Tag=0x01 (not Enumerated)
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getEnumerated();
            throw new AssertionError("Expected IOException due to invalid tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("DER input, Enumerated tag error")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}

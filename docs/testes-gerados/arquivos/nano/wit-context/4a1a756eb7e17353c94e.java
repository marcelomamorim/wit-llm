/* @test    @summary Test for DerInputStream.getInteger() method to verify correct behavior and exception handling.    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntegerValidTag();
        testGetIntegerInvalidTag();
    }

    private static void testGetIntegerValidTag() throws IOException {
        byte[] data = new byte[] { (byte)0x02, 0x01, 0x05 }; // Tag=0x02 (INTEGER), length=1, value=5
        DerInputStream dis = new DerInputStream(data);
        int result = dis.getInteger();
        if (result != 5) {
            throw new AssertionError("Expected 5, but got " + result);
        }
    }

    private static void testGetIntegerInvalidTag() {
        byte[] data = new byte[] { (byte)0x03, 0x01, 0x05 }; // Tag=0x03 (not INTEGER)
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getInteger();
            throw new AssertionError("Expected IOException due to invalid tag, but no exception was thrown.");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("DER input, Integer tag error")) {
                throw new AssertionError("Unexpected exception message: " + message, e);
            }
        }
    }
}

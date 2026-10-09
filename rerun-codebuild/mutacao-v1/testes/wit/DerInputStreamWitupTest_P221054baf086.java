/* @test    @summary Test for DerInputStream.getInteger() method to verify exception handling and normal behavior    @run main DerInputStreamWitupTest_P221054baf086 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest_P221054baf086 {

    public static void main(String[] args) throws Exception {
        testGetIntegerNormal();
        testGetIntegerThrowsIOExceptionOnInvalidTag();
    }

    private static void testGetIntegerNormal() throws Exception {
        byte[] validData = new byte[] { (byte)0x02, 0x01, 0x05 }; // Tag=0x02 (INTEGER), length=1, value=5
        DerInputStream dis = new DerInputStream(validData);
        int result = dis.getInteger();
        if (result != 5) {
            throw new AssertionError("Expected 5, but got " + result);
        }
    }

    private static void testGetIntegerThrowsIOExceptionOnInvalidTag() {
        byte[] invalidData = new byte[] { (byte)0x03, 0x01, 0x05 }; // Tag=0x03 (not INTEGER)
        try {
            DerInputStream dis = new DerInputStream(invalidData);
            dis.getInteger();
            throw new AssertionError("Expected IOException due to invalid tag, but no exception was thrown.");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("DER input, Integer tag error")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}

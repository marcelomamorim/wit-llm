/* @test    @summary Test DerInputStream.getInteger() method for correct behavior and exception handling    @run main DerInputStreamWitupTest_P86eb86957ed5 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest_P86eb86957ed5 {

    public static void main(String[] args) throws Exception {
        testValidInteger();
        testInvalidTag();
    }

    private static void testValidInteger() throws IOException {
        byte[] data = new byte[] { (byte)0x02, 0x01, 0x05 }; // Tag=0x02 (INTEGER), Length=1, Value=0x05
        DerInputStream dis = new DerInputStream(data);
        int value = dis.getInteger();
        if (value != 5) {
            throw new AssertionError("Expected 5, got " + value);
        }
    }

    private static void testInvalidTag() {
        byte[] data = new byte[] { (byte)0x03, 0x01, 0x05 }; // Tag=0x03 (not INTEGER)
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getInteger();
            throw new AssertionError("Expected IOException due to invalid tag");
        } catch (IOException e) {
            if (!e.getMessage().contains("DER input, Integer tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}

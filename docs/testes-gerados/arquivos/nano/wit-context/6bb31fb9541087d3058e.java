/* @test    @summary Test for DerInputStream.getEnumerated() method to verify correct behavior and exception handling.    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidEnumerated();
        testInvalidTagThrowsIOException();
    }

    private static void testValidEnumerated() throws IOException {
        byte[] data = new byte[] { (byte)0x0A, 0x01, 0x05 }; // Tag 0x0A (Enumerated), length 1, value 5
        DerInputStream dis = new DerInputStream(data);
        int result = dis.getEnumerated();
        if (result != 5) {
            throw new AssertionError("Expected 5, but got " + result);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        byte[] data = new byte[] { (byte)0x02, 0x01, 0x05 }; // Tag 0x02 (Integer), not Enumerated
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getEnumerated();
            throw new AssertionError("Expected IOException due to invalid tag, but no exception was thrown.");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("DER input, Enumerated tag error")) {
                throw new AssertionError("Unexpected exception message: " + message, e);
            }
        }
    }
}

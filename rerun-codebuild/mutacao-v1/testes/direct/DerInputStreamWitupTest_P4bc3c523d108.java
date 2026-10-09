/* @test    @summary Test getEnumerated() method of DerInputStream for correct behavior and exception handling    @run main DerInputStreamWitupTest_P4bc3c523d108 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest_P4bc3c523d108 {

    public static void main(String[] args) throws Exception {
        testValidEnumerated();
        testInvalidTag();
        testBufferReadException();
    }

    private static void testValidEnumerated() throws IOException {
        byte[] data = new byte[] { (byte)0x0A, 0x01, 0x05 }; // Tag=0x0A (Enumerated), length=1, value=5
        DerInputStream dis = new DerInputStream(data);
        int result = dis.getEnumerated();
        if (result != 5) {
            throw new AssertionError("Expected 5, got " + result);
        }
    }

    private static void testInvalidTag() {
        byte[] data = new byte[] { (byte)0x02, 0x01, 0x05 }; // Tag=0x02 (Integer), should throw
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getEnumerated();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            if (!e.getMessage().contains("Enumerated")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testBufferReadException() {
        byte[] data = new byte[] { (byte)0x0A }; // Incomplete data, length missing
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getEnumerated();
            throw new AssertionError("Expected IOException for incomplete data");
        } catch (IOException e) {
            // Expected exception due to insufficient data
        }
    }
}

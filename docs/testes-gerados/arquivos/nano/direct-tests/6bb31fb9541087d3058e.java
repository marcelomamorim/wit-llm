/* @test    @summary Test DerInputStream.getEnumerated() method for correct behavior and exception handling    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidEnumerated();
        testInvalidTag();
    }

    private static void testValidEnumerated() throws IOException {
        byte[] data = new byte[] { (byte)0x0A, 0x01, 0x05 }; // Tag 0x0A (Enumerated), length 1, value 5
        DerInputStream dis = new DerInputStream(data);
        int result = dis.getEnumerated();
        if (result != 5) {
            throw new AssertionError("Expected 5, but got " + result);
        }
    }

    private static void testInvalidTag() {
        byte[] data = new byte[] { (byte)0x02, 0x01, 0x05 }; // Tag 0x02 (Integer), length 1, value 5
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getEnumerated();
            throw new AssertionError("Expected IOException due to invalid tag");
        } catch (IOException e) {
            if (!e.getMessage().contains("Enumerated")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}

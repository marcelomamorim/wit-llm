/* @test    @summary Test DerInputStream.getSet method for correct behavior and exception handling    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidGetSet();
        testInvalidTagThrowsIOException();
    }

    private static void testValidGetSet() throws IOException {
        byte[] data = new byte[] { (byte)0x31, 0x0A, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02, 0x02, 0x01, 0x03 };
        DerInputStream dis = new DerInputStream(data);
        DerValue[] values = dis.getSet(0);
        if (values == null || values.length != 3) {
            throw new AssertionError("Expected 3 DerValue objects");
        }
        // Additional checks can be added here if DerValue exposes value details
    }

    private static void testInvalidTagThrowsIOException() {
        byte[] invalidData = new byte[] { (byte)0x30, 0x03, 0x02, 0x01, 0x01 };
        try {
            DerInputStream dis = new DerInputStream(invalidData);
            dis.getSet(0);
            throw new AssertionError("Expected IOException due to invalid tag");
        } catch (IOException e) {
            if (!e.getMessage().contains("Set tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}

/* @test
   @summary Tests getEnumerated() method of DerInputStream for correct behavior.
   @run main DerInputStreamWitupTest_P79231236bc56
 */
import java.io.IOException;
import sun.security.util.DerInputStream;
import java.util.Arrays;

public class DerInputStreamWitupTest_P79231236bc56 {
    public static void main(String[] args) throws Exception {
        testValidEnumerated();
        testInvalidTagThrowsIOException();
    }

    private static void testValidEnumerated() throws IOException {
        byte[] data = new byte[] { (byte)0x0A, 0x01, 0x05 };
        DerInputStream dis = new DerInputStream(data);
        int result = dis.getEnumerated();
        if (result != 5) {
            throw new AssertionError("Expected 5, but got " + result);
        }
    }

    private static void testInvalidTagThrowsIOException() throws IOException {
        byte[] data = new byte[] { (byte)0x02, 0x01, 0x05 };
        DerInputStream dis = new DerInputStream(data);
        try {
            dis.getEnumerated();
            throw new AssertionError("Expected IOException due to invalid tag");
        } catch (IOException e) {
            if (!e.getMessage().contains("Enumerated tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}

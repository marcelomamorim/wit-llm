/* @test
   @summary Tests getInteger() method of DerInputStream for correct behavior.
   @run main DerInputStreamWitupTest_P4eda73210fe9
 */
import java.io.IOException;
import sun.security.util.DerInputStream;
import java.util.Random;

public class DerInputStreamWitupTest_P4eda73210fe9 {
    public static void main(String[] args) throws Exception {
        testValidInteger();
        testInvalidTag();
        testBufferWithDifferentData();
    }

    private static void testValidInteger() throws IOException {
        byte[] data = new byte[] { (byte)0x02, 0x01, 0x05 }; // Tag=0x02 (Integer), Length=1, Value=5
        DerInputStream dis = new DerInputStream(data);
        int value = dis.getInteger();
        if (value != 5) {
            throw new AssertionError("Expected 5, got " + value);
        }
    }

    private static void testInvalidTag() {
        byte[] data = new byte[] { (byte)0x03, 0x01, 0x05 }; // Tag=0x03 (not Integer)
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getInteger();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            if (!e.getMessage().contains("DER input, Integer tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testBufferWithDifferentData() throws IOException {
        byte[] data = new byte[] { (byte)0x02, 0x02, 0x01, 0x02 }; // Tag=0x02, Length=2, Value=0x01 0x02
        DerInputStream dis = new DerInputStream(data);
        int value = dis.getInteger();
        if (value != 258) { // 0x0102 in decimal
            throw new AssertionError("Expected 258, got " + value);
        }
    }
}

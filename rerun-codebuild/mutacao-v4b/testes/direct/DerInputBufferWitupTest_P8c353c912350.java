/* @test
   @summary Test getUTCTime method of DerInputBuffer for valid and invalid inputs.
   @run main DerInputBufferWitupTest_P8c353c912350
 */
import java.util.Date;
import java.io.IOException;
import sun.security.util.DerInputBuffer;
public class DerInputBufferWitupTest_P8c353c912350 {
    public static void main(String[] args) throws Exception {
        testValidUTCTime();
        testShortRead();
        testInvalidLength();
    }

    private static void testValidUTCTime() throws Exception {
        byte[] validData = new byte[] { 0x17, 0x0D, 0x07, 0xE4, 0x07, 0x0A, 0x0A, 0x0A, 0x0A, 0x0A, 0x0A, 0x5A, 0x5A, 0x5A };
        DerInputBuffer buffer = new DerInputBuffer(validData);
        Date date = buffer.getUTCTime(validData.length);
        if (date == null) {
            throw new AssertionError("Expected non-null date for valid data");
        }
    }

    private static void testShortRead() {
        byte[] data = new byte[] { 0x17, 0x0D, 0x07, 0xE4 };
        DerInputBuffer buffer = new DerInputBuffer(data);
        try {
            buffer.getUTCTime(20); // len > available()
            throw new AssertionError("Expected IOException for short read");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testInvalidLength() {
        byte[] data = new byte[] { 0x17, 0x05, 0x07, 0xE4, 0x07 };
        DerInputBuffer buffer = new DerInputBuffer(data);
        try {
            buffer.getUTCTime(5); // len < 11
            throw new AssertionError("Expected IOException for invalid length");
        } catch (IOException e) {
            // expected
        }
        try {
            buffer.getUTCTime(20); // len > 17
            throw new AssertionError("Expected IOException for invalid length");
        } catch (IOException e) {
            // expected
        }
    }
}

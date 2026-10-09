/* @test    @summary Test sun.security.util.DerInputBuffer.getUTCTime(int) for valid and invalid inputs    @run main DerInputBufferWitupTest_Padc6e2411776 */

import sun.security.util.DerInputBuffer;
import java.io.IOException;
import java.util.Date;

public class DerInputBufferWitupTest_Padc6e2411776 {

    public static void main(String[] args) {
        testValidUTCTime();
        testShortRead();
        testInvalidLength();
    }

    static void testValidUTCTime() {
        byte[] data = {
            0x17, 0x0D, '2', '0', '2', '3', '0', '1', '0', '1', '0', '0', '0', '0'
        };
        DerInputBuffer buffer = new DerInputBuffer(data);
        try {
            Date date = buffer.getUTCTime(data.length - 1);
            if (date == null) {
                throw new AssertionError("Expected non-null Date for valid input");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    static void testShortRead() {
        byte[] data = {
            0x17, 0x0D, '2', '0', '2', '3', '0', '1', '0', '1', '0', '0', '0'
        };
        DerInputBuffer buffer = new DerInputBuffer(data);
        try {
            buffer.getUTCTime(20); // length greater than available
            throw new AssertionError("Expected IOException for short read");
        } catch (IOException e) {
            // expected
        }
    }

    static void testInvalidLength() {
        byte[] data = {
            0x17, 0x05, '2', '0', '2', '3', '0'
        };
        DerInputBuffer buffer = new DerInputBuffer(data);
        try {
            buffer.getUTCTime(data.length - 1); // length less than 11
            throw new AssertionError("Expected IOException for invalid length");
        } catch (IOException e) {
            // expected
        }
    }
}

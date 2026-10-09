/* @test    @summary Test getUTCTime method of DerInputBuffer for valid and invalid inputs    @run main DerInputBufferWitupTest_Pcf0e4aa14e64 */

import sun.security.util.DerInputBuffer;
import java.io.IOException;
import java.util.Date;

public class DerInputBufferWitupTest_Pcf0e4aa14e64 {

    public static void main(String[] args) {
        testValidUTCTime();
        testShortRead();
        testInvalidLength();
    }

    static void testValidUTCTime() {
        try {
            byte[] validData = new byte[] { '0', '1', '0', '1', '1', '2', '3', '5', '9', 'Z' };
            DerInputBuffer buffer = new DerInputBuffer(validData);
            Date date = buffer.getUTCTime(validData.length);
            if (date == null) {
                throw new AssertionError("Expected valid Date, got null");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in valid test: " + e);
        }
    }

    static void testShortRead() {
        try {
            byte[] data = new byte[] { '0', '1', '0', '1' };
            DerInputBuffer buffer = new DerInputBuffer(data);
            buffer.getUTCTime(10); // length greater than available
            throw new AssertionError("Expected IOException for short read not thrown");
        } catch (IOException e) {
            if (!e.getMessage().contains("short read")) {
                throw new AssertionError("Unexpected exception message: " + e);
            }
        }
    }

    static void testInvalidLength() {
        try {
            byte[] data = new byte[] { '0', '1', '0', '1', '1', '2', '3', '5', '9', 'Z' };
            DerInputBuffer buffer = new DerInputBuffer(data);
            buffer.getUTCTime(5); // length less than 11
            throw new AssertionError("Expected IOException for invalid length not thrown");
        } catch (IOException e) {
            if (!e.getMessage().contains("length error")) {
                throw new AssertionError("Unexpected exception message: " + e);
            }
        }
    }
}

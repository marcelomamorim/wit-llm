/* @test    @summary Test getUTCTime method for exception paths and behavior    @run main DerInputBufferWitupTest_Pcf0e4aa14e64 */

import sun.security.util.DerInputBuffer;
import java.io.IOException;
import java.util.Date;

public class DerInputBufferWitupTest_Pcf0e4aa14e64 {

    public static void main(String[] args) {
        testGetUTCTimeShortRead();
        testGetUTCTimeInvalidLength();
    }

    private static void testGetUTCTimeShortRead() {
        byte[] buf = {0x30, 0x0A}; // dummy data
        DerInputBuffer dib = new DerInputBuffer(buf);
        try {
            // Simulate available() returning less than len
            DerInputBuffer spy = new DerInputBuffer(buf) {
                @Override
                public int available() {
                    return 5; // less than len=11
                }
            };
            spy.getUTCTime(11);
            throw new AssertionError("Expected IOException for short read");
        } catch (IOException e) {
            if (!e.getMessage().contains("short read")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testGetUTCTimeInvalidLength() {
        byte[] buf = {0x30, 0x0A}; // dummy data
        DerInputBuffer dib = new DerInputBuffer(buf);
        try {
            // Test with length less than 11
            dib.getUTCTime(10);
            throw new AssertionError("Expected IOException for length < 11");
        } catch (IOException e) {
            if (!e.getMessage().contains("length error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        try {
            // Test with length greater than 17
            dib.getUTCTime(18);
            throw new AssertionError("Expected IOException for length > 17");
        } catch (IOException e) {
            if (!e.getMessage().contains("length error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}

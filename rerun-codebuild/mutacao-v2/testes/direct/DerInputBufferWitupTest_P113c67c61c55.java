/*
 * @test
 * @summary Test sun.security.util.DerInputBuffer.getUTCTime(int) method
 * @run main DerInputBufferWitupTest_P113c67c61c55
 */

import sun.security.util.DerInputBuffer;
import java.io.IOException;
import java.util.Date;

public class DerInputBufferWitupTest_P113c67c61c55 {
    public static void main(String[] args) throws Exception {
        testGetUTCTimeValidLength();
        testGetUTCTimeLengthTooShort();
        testGetUTCTimeLengthTooLong();
        testGetUTCTimeLengthExceedsAvailable();
    }

    static void testGetUTCTimeValidLength() throws Exception {
        // Prepare a buffer with enough bytes (length between 11 and 17)
        byte[] buf = new byte[20];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = '0';
        }
        DerInputBuffer dib = new DerInputBuffer(buf);

        // Call getUTCTime with valid length 11
        Date d = dib.getUTCTime(11);
        if (d == null) {
            throw new RuntimeException("getUTCTime returned null for valid length");
        }
    }

    static void testGetUTCTimeLengthTooShort() {
        byte[] buf = new byte[20];
        DerInputBuffer dib = new DerInputBuffer(buf);
        try {
            dib.getUTCTime(10); // less than 11
            throw new RuntimeException("Expected IOException for length < 11");
        } catch (IOException e) {
            if (!"DER UTC Time length error".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testGetUTCTimeLengthTooLong() {
        byte[] buf = new byte[20];
        DerInputBuffer dib = new DerInputBuffer(buf);
        try {
            dib.getUTCTime(18); // greater than 17
            throw new RuntimeException("Expected IOException for length > 17");
        } catch (IOException e) {
            if (!"DER UTC Time length error".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testGetUTCTimeLengthExceedsAvailable() {
        byte[] buf = new byte[5];
        DerInputBuffer dib = new DerInputBuffer(buf);
        try {
            dib.getUTCTime(6); // length > available
            throw new RuntimeException("Expected IOException for length > available");
        } catch (IOException e) {
            if (!"short read of DER UTC Time".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}

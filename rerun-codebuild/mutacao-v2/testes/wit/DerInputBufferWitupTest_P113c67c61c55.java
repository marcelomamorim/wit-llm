/*
 * @test
 * @summary Test sun.security.util.DerInputBuffer.getUTCTime(int) for valid and invalid inputs
 * @run main DerInputBufferWitupTest_P113c67c61c55
 */

import java.io.IOException;
import java.util.Date;
import sun.security.util.DerInputBuffer;

public class DerInputBufferWitupTest_P113c67c61c55 {

    public static void main(String[] args) throws Exception {
        testGetUTCTimeValidLength();
        testGetUTCTimeLengthTooShort();
        testGetUTCTimeLengthTooLong();
        testGetUTCTimeLengthExceedsAvailable();
        System.out.println("All tests passed.");
    }

    private static void testGetUTCTimeValidLength() throws IOException {
        // Prepare a buffer with at least 11 bytes (minimum valid length)
        byte[] buf = new byte[11];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = '0';
        }
        DerInputBuffer dib = new DerInputBuffer(buf);

        // Should not throw for length 11
        Date d = dib.getUTCTime(11);
        if (d == null) {
            throw new AssertionError("getUTCTime returned null for valid length");
        }

        // Prepare a buffer with 17 bytes (maximum valid length)
        buf = new byte[17];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = '0';
        }
        dib = new DerInputBuffer(buf);

        // Should not throw for length 17
        d = dib.getUTCTime(17);
        if (d == null) {
            throw new AssertionError("getUTCTime returned null for valid length");
        }
    }

    private static void testGetUTCTimeLengthTooShort() {
        byte[] buf = new byte[11];
        DerInputBuffer dib = new DerInputBuffer(buf);
        try {
            dib.getUTCTime(10); // length less than 11
            throw new AssertionError("Expected IOException for length < 11");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testGetUTCTimeLengthTooLong() {
        byte[] buf = new byte[18];
        DerInputBuffer dib = new DerInputBuffer(buf);
        try {
            dib.getUTCTime(18); // length greater than 17
            throw new AssertionError("Expected IOException for length > 17");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testGetUTCTimeLengthExceedsAvailable() {
        byte[] buf = new byte[15];
        DerInputBuffer dib = new DerInputBuffer(buf);
        try {
            dib.getUTCTime(16); // length exceeds available (15)
            throw new AssertionError("Expected IOException for length > available");
        } catch (IOException e) {
            // expected
        }
    }
}

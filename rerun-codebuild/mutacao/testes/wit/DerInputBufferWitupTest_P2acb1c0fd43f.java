/*
 * @test
 * @summary Tests for sun.security.util.DerInputBuffer.getUTCTime(int)
 * @run main DerInputBufferWitupTest_P2acb1c0fd43f
 */

import java.io.IOException;
import java.util.Date;
import sun.security.util.DerInputBuffer;

public class DerInputBufferWitupTest_P2acb1c0fd43f {

    public static void main(String[] args) throws Exception {
        testGetUTCTimeShortRead();
        testGetUTCTimeInvalidLength();
    }

    private static void testGetUTCTimeShortRead() {
        byte[] data = {0x30, 0x39}; // dummy data
        DerInputBuffer buffer = new DerInputBuffer(data);
        try {
            // len exceeds available data, should throw IOException
            buffer.getUTCTime(100);
            System.err.println("Failed: Expected IOException for short read");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testGetUTCTimeInvalidLength() {
        byte[] data = {0x30, 0x39}; // dummy data
        DerInputBuffer buffer = new DerInputBuffer(data);
        try {
            // length less than 11, should throw IOException
            buffer.getUTCTime(10);
            System.err.println("Failed: Expected IOException for length < 11");
        } catch (IOException e) {
            // expected
        }
        try {
            // length greater than 17, should throw IOException
            buffer.getUTCTime(20);
            System.err.println("Failed: Expected IOException for length > 17");
        } catch (IOException e) {
            // expected
        }
    }
}

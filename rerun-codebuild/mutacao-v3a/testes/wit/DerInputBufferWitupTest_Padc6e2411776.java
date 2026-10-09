/*
 * @test
 * @summary Tests for sun.security.util.DerInputBuffer.getUTCTime(int)
 * @run main DerInputBufferWitupTest_Padc6e2411776
 */

import java.io.IOException;
import java.util.Date;
import sun.security.util.DerInputBuffer;

public class DerInputBufferWitupTest_Padc6e2411776 {

    public static void main(String[] args) throws IOException {
        testGetUTCTimeShorter();
        testGetUTCTimeTooShort();
        testGetUTCTimeTooLong();
    }

    static void testGetUTCTimeShorter() {
        byte[] buf = new byte[20];
        DerInputBuffer dib = new DerInputBuffer(buf);
        int available = buf.length;
        try {
            Date date = new DerInputBuffer(buf).getUTCTime(available - 1);
            if (date == null) {
                System.out.println("FAIL: getUTCTime did not throw for short read");
            }
        } catch (IOException e) {
            // Expected exception
            System.out.println("PASS: getUTCTime short read throws IOException as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testGetUTCTimeTooShort() {
        byte[] buf = new byte[20];
        DerInputBuffer dib = new DerInputBuffer(buf);
        try {
            // length less than 11
            new DerInputBuffer(buf).getUTCTime(10);
            System.out.println("FAIL: getUTCTime did not throw for length < 11");
        } catch (IOException e) {
            // Expected
            System.out.println("PASS: getUTCTime throws IOException for length < 11");
        }
    }

    static void testGetUTCTimeTooLong() {
        byte[] buf = new byte[20];
        DerInputBuffer dib = new DerInputBuffer(buf);
        try {
            // length greater than 17
            new DerInputBuffer(buf).getUTCTime(18);
            System.out.println("FAIL: getUTCTime did not throw for length > 17");
        } catch (IOException e) {
            // Expected
            System.out.println("PASS: getUTCTime throws IOException for length > 17");
        }
    }
}

/* @test    @summary Test getUTCTime method for various scenarios    @run main DerValueWitupTest */

import java.io.*;
import java.util.Date;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testGetUTCTimeWithValidTag();
        testGetUTCTimeWithInvalidTag();
        testGetUTCTimeWithBufferException();
    }

    private static void testGetUTCTimeWithValidTag() {
        try {
            byte[] buf = new byte[] { (byte)0x17, 0x0D, 0x30, 0x33, 0x30, 0x39, 0x30, 0x33, 0x31, 0x39, 0x35, 0x39, 0x5A };
            DerValue dv = new DerValue(buf);
            Date date = dv.getUTCTime();
            if (date == null) {
                throw new AssertionError("Expected non-null Date");
            }
            System.out.println("testGetUTCTimeWithValidTag passed.");
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }

    private static void testGetUTCTimeWithInvalidTag() {
        try {
            byte[] buf = new byte[] { (byte)0x19, 0x0D, 0x30, 0x33, 0x30, 0x39, 0x30, 0x33, 0x31, 0x39, 0x35, 0x39, 0x5A };
            DerValue dv = new DerValue(buf);
            dv.getUTCTime();
            throw new AssertionError("Expected IOException due to invalid tag");
        } catch (IOException e) {
            System.out.println("testGetUTCTimeWithInvalidTag passed.");
        }
    }

    private static void testGetUTCTimeWithBufferException() {
        try {
            byte[] buf = new byte[] { (byte)0x17, 0x0D };
            DerValue dv = new DerValue(buf);
            dv.buffer = new DerInputBuffer(new byte[] { 0x00 }); // Mock buffer with insufficient data
            dv.data = new DerInputStream(new ByteArrayInputStream(new byte[] { 0x00 }));
            dv.getUTCTime();
            throw new AssertionError("Expected IOException due to buffer issue");
        } catch (IOException e) {
            System.out.println("testGetUTCTimeWithBufferException passed.");
        }
    }
}

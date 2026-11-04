/* @test    @summary Test DerValue.getUTCTime() method for correct behavior and exception handling    @run main DerValueWitupTest */

import java.io.*;
import java.util.Date;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testValidUTCTime();
        testInvalidTagThrowsException();
    }

    private static void testValidUTCTime() {
        try {
            byte[] utcTimeBytes = new byte[] { (byte)0x17, 0x0D, 0x32, 0x30, 0x30, 0x39, 0x30, 0x31, 0x30, 0x31, 0x30, 0x30, 0x5A };
            DerValue dv = new DerValue(utcTimeBytes);
            dv.tag = DerValue.tag_UtcTime;
            Date date = dv.getUTCTime();
            if (date == null) {
                throw new AssertionError("Expected non-null Date for valid UTCTime");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for valid UTCTime: " + e);
        }
    }

    private static void testInvalidTagThrowsException() {
        try {
            byte[] invalidBytes = new byte[] { (byte)0x17, 0x0D, 0x32, 0x30, 0x30, 0x39, 0x30, 0x31, 0x30, 0x31, 0x30, 0x30, 0x5A };
            DerValue dv = new DerValue(invalidBytes);
            dv.tag = (byte)0xFF; // invalid tag
            dv.getUTCTime();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("not a UtcTime")) {
                throw new AssertionError("Unexpected exception message: " + e);
            }
        }
    }
}

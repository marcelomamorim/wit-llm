/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getUTCTime() method
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;
import java.util.Date;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidUTCTime();
        testInvalidTagThrowsIOException();
    }

    private static void testValidUTCTime() throws Exception {
        // Construct a valid DER encoding of a UTCTime value
        // Tag for UTCTime is 0x17
        // Example UTCTime string: "210101000000Z" (Jan 1, 2021 00:00:00 UTC)
        // DER encoding: tag (0x17), length (11), value (ASCII bytes)
        byte[] utcTimeBytes = new byte[] {
            DerValue.tag_UtcTime, 11,
            '2','1','0','1','0','1','0','0','0','0','0','Z'
        };

        DerInputStream derInput = new DerInputStream(utcTimeBytes);
        Date date = derInput.getUTCTime();
        if (date == null) {
            throw new RuntimeException("getUTCTime returned null for valid input");
        }
        // We expect the date to be roughly Jan 1, 2021 UTC
        // Check year is 2021
        @SuppressWarnings("deprecation")
        int year = date.getYear() + 1900;
        if (year != 2021) {
            throw new RuntimeException("Expected year 2021 but got " + year);
        }
    }

    private static void testInvalidTagThrowsIOException() throws Exception {
        // Construct a DER encoding with invalid tag (not UTCTime)
        byte[] invalidTagBytes = new byte[] {
            0x16, 0 // tag 0x16 (not UTCTime), length 0
        };

        DerInputStream derInput = new DerInputStream(invalidTagBytes);
        try {
            derInput.getUTCTime();
            throw new RuntimeException("Expected IOException for invalid tag but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("UTCtime tag invalid")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
        }
    }
}

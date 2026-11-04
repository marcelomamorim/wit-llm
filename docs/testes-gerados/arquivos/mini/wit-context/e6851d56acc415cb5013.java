/*
 * @test
 * @summary Test sun.security.util.DerValue.getUTCTime() behavior and exception paths
 * @run main sun.security.util.DerValueWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.DerInputBuffer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetUTCTimeWithInvalidTag();
        testGetUTCTimeWithValidTag();
    }

    private static void testGetUTCTimeWithInvalidTag() {
        // Create DerValue with tag != 23 (tag_UtcTime == 23)
        // Use tag 22 (arbitrary invalid tag for UTC time)
        byte invalidTag = 22;
        byte[] dummyData = {0x00};
        DerValue dv = new DerValue(invalidTag, dummyData);
        try {
            dv.getUTCTime();
            throw new RuntimeException("Expected IOException for invalid tag not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not a UtcTime")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }

    private static void testGetUTCTimeWithValidTag() throws Exception {
        // tag_UtcTime is 23
        byte validTag = 23;

        // Prepare a valid DER-encoded UTC time string
        // Format: YYMMDDHHMMSSZ (13 bytes) + tag and length bytes
        // We only need the content bytes for DerValue constructor
        // Example: "210101120000Z" (Jan 1, 2021 12:00:00 UTC)
        // UTC time string in ASCII
        byte[] utcTimeBytes = new byte[] {
            '2','1','0','1','0','1','1','2','0','0','0','0','Z'
        };

        DerValue dv = new DerValue(validTag, utcTimeBytes);

        // The getUTCTime method calls buffer.getUTCTime(data.available())
        // We expect it to parse the date correctly without exception
        Date date = dv.getUTCTime();
        if (date == null) {
            throw new RuntimeException("getUTCTime returned null for valid input");
        }

        // Check that the year is 2021 (approximate check)
        // Date.getYear() returns year since 1900
        int year = date.getYear() + 1900;
        if (year != 2021) {
            throw new RuntimeException("Parsed year incorrect, expected 2021 but got " + year);
        }
    }
}

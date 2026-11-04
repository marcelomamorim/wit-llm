/*
 * @test
 * @summary Test sun.security.util.DerValue.getUTCTime() method behavior
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.DerInputBuffer;
import sun.security.util.DerInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetUTCTimeWithValidTag();
        testGetUTCTimeWithInvalidTag();
    }

    private static void testGetUTCTimeWithValidTag() throws Exception {
        // Construct a DER encoding of a UTCTime value
        // UTCTime tag is 0x17
        byte utcTimeTag = DerValue.tag_UtcTime;
        // Example UTCTime string: "210101000000Z" (Jan 1, 2021 00:00:00 UTC)
        // DER encoding: tag + length + value
        byte[] utcTimeValue = new byte[] {
            utcTimeTag, 13, // tag and length
            '2','1','0','1','0','1','0','0','0','0','0','0','Z'
        };

        DerValue derValue = new DerValue(utcTimeValue);

        Date date = derValue.getUTCTime();
        if (date == null) {
            throw new RuntimeException("getUTCTime returned null for valid UTCTime");
        }
        // We expect the date to be Jan 1, 2021 00:00:00 UTC
        // We check year, month, day, hour, minute, second
        @SuppressWarnings("deprecation")
        int year = date.getYear() + 1900;
        @SuppressWarnings("deprecation")
        int month = date.getMonth() + 1;
        @SuppressWarnings("deprecation")
        int day = date.getDate();
        @SuppressWarnings("deprecation")
        int hour = date.getHours();
        @SuppressWarnings("deprecation")
        int minute = date.getMinutes();
        @SuppressWarnings("deprecation")
        int second = date.getSeconds();

        if (year != 2021 || month != 1 || day != 1 || hour != 0 || minute != 0 || second != 0) {
            throw new RuntimeException("getUTCTime returned unexpected date: " + date);
        }
    }

    private static void testGetUTCTimeWithInvalidTag() {
        // Construct a DerValue with a tag different from tag_UtcTime
        // Use tag_Integer (0x02) for example
        byte invalidTag = DerValue.tag_Integer;
        byte[] intValue = new byte[] {invalidTag, 1, 0x01};

        try {
            DerValue derValue = new DerValue(intValue);
            derValue.getUTCTime();
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not a UtcTime")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
            // Expected exception, test passed
        }
    }
}

/*
 * @test
 * @summary Verify DerValue.getUTCTime decodes a UTCTime value and rejects values with a different tag
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testValidUtcTime();
        testNonUtcTagThrowsIOException();
    }

    static void testValidUtcTime() throws Exception {
        byte[] utc = "910506234540Z".getBytes("US-ASCII");
        DerValue v = new DerValue((byte) 0x17, utc);
        Date d = v.getUTCTime();
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.clear();
        c.set(1991, Calendar.MAY, 6, 23, 45, 40);
        if (d.getTime() != c.getTimeInMillis()) {
            throw new RuntimeException("Unexpected UTCTime decoded: " + d.getTime()
                    + " expected: " + c.getTimeInMillis());
        }
    }

    static void testNonUtcTagThrowsIOException() throws Exception {
        byte[] data = "910506234540Z".getBytes("US-ASCII");
        DerValue iv = new DerValue((byte) 0x02, data);
        boolean thrown = false;
        try {
            iv.getUTCTime();
        } catch (IOException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IOException not thrown for non-UtcTime tag");
        }
    }
}
